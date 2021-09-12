package ru.zotov.carracing.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.zotov.carracing.security.config.SecurityConfig;

/**
 * @author Created by ZotovES on 12.09.2021
 */
@SpringBootApplication
@EnableFeignClients
@ComponentScan(basePackages = {"ru.zotov.carracing.security", "ru.zotov.carracing.store", "ru.zotov.carracing.common.mapper"})
@ImportAutoConfiguration(SecurityConfig.class)
public class CarRacingStoreApp {
    public static void main(String[] args) {
        SpringApplication.run(CarRacingStoreApp.class, args);
    }
}
