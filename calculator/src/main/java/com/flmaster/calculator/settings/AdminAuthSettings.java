package com.flmaster.calculator.settings;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@ConfigurationProperties("settings.admin")
public record AdminAuthSettings(
        String login,
        String password
) {
}
