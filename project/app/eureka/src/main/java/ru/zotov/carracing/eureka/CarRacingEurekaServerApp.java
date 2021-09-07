package ru.zotov.carracing.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author Created by ZotovES on 07.09.2021
 */
@SpringBootApplication
@EnableEurekaServer
public class CarRacingEurekaServerApp {
    public static void main(String[] args) {
        SpringApplication.run(CarRacingEurekaServerApp.class, args);
    }
}
