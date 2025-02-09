package com.dnd.sbooky.clients.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.dnd.sbooky.clients")
class FeignClientConfig {}
