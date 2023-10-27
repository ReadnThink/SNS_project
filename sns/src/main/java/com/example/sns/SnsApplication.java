package com.example.sns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@ComponentScan(basePackages = {"com.example.sns", "com.example.core","com.example.query"})
public class SnsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SnsApplication.class, args);
    }

}
