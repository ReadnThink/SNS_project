package com.example.sns_project.config;

import com.querydsl.core.annotations.Config;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Base64;

@Data
@ConfigurationProperties(prefix = "talk")
public class AppConfig {

    private byte[] jwtKey;

    public void setJwtKey(final String jwtKey) {
        this.jwtKey = Base64.getDecoder().decode(jwtKey);
    }

    public byte[] getJwtKey() {
        return jwtKey;
    }
}
