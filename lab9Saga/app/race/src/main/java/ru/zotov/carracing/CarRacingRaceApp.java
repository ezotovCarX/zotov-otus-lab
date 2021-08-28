package ru.zotov.carracing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class CarRacingRaceApp {

    public static void main(String[] args) {
        SpringApplication.run(CarRacingRaceApp.class, args);
    }

}