package com.example.sns_project;

import com.example.sns_project.global.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(AppConfig.class)
@SpringBootApplication
public class SnsProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SnsProjectApplication.class, args);
    }

}

