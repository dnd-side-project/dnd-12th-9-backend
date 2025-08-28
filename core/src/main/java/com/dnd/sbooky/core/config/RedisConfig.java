package com.dnd.sbooky.core.config;

import static java.time.Duration.ofMillis;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.ClientOptions.DisconnectedBehavior;
import io.lettuce.core.ReadFrom;
import io.lettuce.core.SocketOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@RequiredArgsConstructor
public class RedisConfig {

    private final RedisProperties redisProperties;

    private static final Long CONNECT_TIMEOUT_MILLIS = 1000L;
    private static final Long COMMAND_TIMEOUT_MILLIS = 3000L;
    private static final Long SHUTDOWN_TIMEOUT_MILLIS = 100L;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {

        SocketOptions socketOptions =
                SocketOptions.builder()
                        .connectTimeout(ofMillis(CONNECT_TIMEOUT_MILLIS))
                        .keepAlive(true)
                        .build();

        ClientOptions clientOptions =
                ClientOptions.builder()
                        .autoReconnect(true)
                        .pingBeforeActivateConnection(true)
                        .disconnectedBehavior(DisconnectedBehavior.REJECT_COMMANDS)
                        .socketOptions(socketOptions)
                        .build();

        LettuceClientConfiguration clientConfiguration =
                LettuceClientConfiguration.builder()
                        .commandTimeout(ofMillis(COMMAND_TIMEOUT_MILLIS))
                        .readFrom(ReadFrom.REPLICA_PREFERRED)
                        .shutdownTimeout(ofMillis(SHUTDOWN_TIMEOUT_MILLIS))
                        .clientOptions(clientOptions)
                        .build();

        LettuceConnectionFactory factory =
                new LettuceConnectionFactory(sentinelConfiguration(), clientConfiguration);
        factory.setValidateConnection(true);
        return factory;
    }

    private RedisSentinelConfiguration sentinelConfiguration() {
        RedisSentinelConfiguration sentinelConfiguration =
                new RedisSentinelConfiguration().master(redisProperties.getSentinel().getMaster());

        redisProperties
                .getSentinel()
                .getNodes()
                .forEach(node -> sentinelConfiguration.sentinel(node.getHost(), node.getPort()));

        sentinelConfiguration.setPassword(RedisPassword.of(redisProperties.getPassword()));
        sentinelConfiguration.setSentinelPassword(RedisPassword.of(redisProperties.getPassword()));
        return sentinelConfiguration;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(
            RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        template.afterPropertiesSet();
        return template;
    }
}
