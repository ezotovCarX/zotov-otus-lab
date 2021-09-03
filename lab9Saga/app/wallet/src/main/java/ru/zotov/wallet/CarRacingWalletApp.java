package ru.zotov.wallet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.annotation.EnableKafka;
import ru.zotov.carracing.security.config.SecurityConfig;

@EnableKafka
@SpringBootApplication(scanBasePackages = {"ru.zotov.wallet", "ru.zotov.carracing.security",
                                           "ru.zotov.carracing.common.mapper"})
@EntityScan(basePackages = "ru.zotov.wallet.entity")
@EnableJpaRepositories(basePackages = "ru.zotov.wallet.repo")
@ImportAutoConfiguration(SecurityConfig.class)
public class CarRacingWalletApp {

    public static void main(String[] args) {
        SpringApplication.run(CarRacingWalletApp.class, args);
    }

}
