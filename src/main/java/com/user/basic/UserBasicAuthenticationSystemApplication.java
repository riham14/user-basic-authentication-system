package com.user.basic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan({"com.user.basic.authentication"})
@SpringBootApplication
public class UserBasicAuthenticationSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserBasicAuthenticationSystemApplication.class, args);
    }

}