package com.dnd.sbooky.api.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "redirect")
public class RedirectProperties {

    private String local;
    private String prod;
    private String dev;
}
