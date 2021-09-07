package ru.zotov.carracing.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Created by ZotovES on 07.09.2021
 */
@EnableDiscoveryClient
@SpringBootApplication
public class CarRacingApiGateway {
    public static void main(String[] args) {
        SpringApplication.run(CarRacingApiGateway.class, args);
    }
}
