package com.dnd.book.docs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
public class SwaggerConfig {


    @Bean
    @Profile("!prod") // prod 환경에서는 Swagger 비활성
    public OpenAPI customOpenAPI() {

        // todo: Spring Security 적용 시 인증 토큰 관련 정보를 추가해야 한다.
        return new OpenAPI()
                .info(info())
                .servers(servers());
    }

    private Info info() {
        return new Info()
                .title("Book API")
                .description("[DND 12-9] 프로젝트 API 명세서입니다.")
                .version("v1.0.0");
    }

    private List<Server> servers() {
        Server devServer = new Server()
                .url("/dev.url.com")
                .description("개발 환경 서버 URL");

        Server prodServer = new Server()
                .url("/prod.url.com")
                .description("운영 환경 서버 URL");

        return List.of(devServer, prodServer);
    }
}
