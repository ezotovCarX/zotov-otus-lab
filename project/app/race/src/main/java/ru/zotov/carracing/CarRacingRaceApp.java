package ru.zotov.carracing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import ru.zotov.carracing.security.config.SecurityConfig;

@EnableKafka
@SpringBootApplication
@ImportAutoConfiguration(SecurityConfig.class)
public class CarRacingRaceApp {

    public static void main(String[] args) {
        SpringApplication.run(CarRacingRaceApp.class, args);
    }

}
