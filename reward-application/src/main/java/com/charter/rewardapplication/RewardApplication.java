package com.charter.rewardapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@Configuration
@EnableAsync
public class RewardApplication {

    public static void main(String[] args) {
        SpringApplication.run(RewardApplication.class, args);
    }

}
