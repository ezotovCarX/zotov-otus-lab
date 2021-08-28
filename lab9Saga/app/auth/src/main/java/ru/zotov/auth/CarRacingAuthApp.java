package ru.zotov.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
@ComponentScan({"ru.zotov.carracing.common.mapper", "ru.zotov.auth"})
public class CarRacingAuthApp {

    public static void main(String[] args) {
        SpringApplication.run(CarRacingAuthApp.class, args);
    }

}
