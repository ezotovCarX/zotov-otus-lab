package ru.zotov.carracing.profile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import ru.zotov.carracing.security.config.SecurityConfig;

@SpringBootApplication
@ComponentScan(basePackages = {"ru.zotov.carracing.security", "ru.zotov.carracing.profile", "ru.zotov.carracing.common.mapper"})
@ImportAutoConfiguration(SecurityConfig.class)
public class CarRacingProfileApp {

    public static void main(String[] args) {
        SpringApplication.run(CarRacingProfileApp.class, args);
    }

}
