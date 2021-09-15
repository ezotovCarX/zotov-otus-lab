package ru.zotov.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.kafka.annotation.EnableKafka;
import ru.zotov.carracing.security.config.SecurityConfig;

@EnableKafka
@EnableCaching
@SpringBootApplication
@ComponentScan({"ru.zotov.carracing.common.mapper", "ru.zotov.auth", "ru.zotov.carracing.security"})
@Import(SecurityConfig.class)
public class CarRacingAuthApp {

    public static void main(String[] args) {
        SpringApplication.run(CarRacingAuthApp.class, args);
    }

}
