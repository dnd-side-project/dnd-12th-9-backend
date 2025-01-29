package com.dnd.book.docs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import jakarta.servlet.ServletContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
public class SwaggerConfig {


    @Bean
    @Profile("!prod") // prod 환경에서는 Swagger 비활성
    public OpenAPI customOpenAPI(ServletContext servletContext) {
        String contextPath = servletContext.getContextPath();
        Server server = new Server().url(contextPath);
        return new OpenAPI()
                .info(info())
                .servers(List.of(server));
    }

    private Info info() {
        License license = new License();
        license.setUrl("https://github.com/dnd-side-project/dnd-12th-9-backend");
        license.setName("Sbooky API License");

        return new Info()
                .title("Sbooky API")
                .description("[DND 12-9] 프로젝트 API 명세서입니다.")
                .version("v1.0.0");
    }
}
