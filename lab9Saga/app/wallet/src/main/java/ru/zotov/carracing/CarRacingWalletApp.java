package ru.zotov.carracing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class CarRacingWalletApp {

    public static void main(String[] args) {
        SpringApplication.run(CarRacingWalletApp.class, args);
    }

}
