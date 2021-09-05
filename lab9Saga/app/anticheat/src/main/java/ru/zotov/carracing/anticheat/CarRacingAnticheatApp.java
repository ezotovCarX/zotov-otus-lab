package ru.zotov.carracing.anticheat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication(scanBasePackages = {"ru.zotov.carracing.common.mapper", "ru.zotov.carracing.anticheat"})
public class CarRacingAnticheatApp {

    public static void main(String[] args) {
        SpringApplication.run(CarRacingAnticheatApp.class, args);
    }

}
