package com.dnd.sbooky;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class SbookyApplication {
    public static void main(String[] args) {
        SpringApplication.run(SbookyApplication.class, args);
    }
}
