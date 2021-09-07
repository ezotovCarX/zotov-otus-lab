package ru.zotov.carracing.gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

/**
 * @author Created by ZotovES on 07.09.2021
 */
//@Configuration
class ProxyConfig {
    @Bean
    RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("random_animal_route", route -> route.path("/race/**")
                        .filters(filter -> filter.stripPrefix(1))
                        .uri("lb://game-car-racing-race"))
                .route("zoo_route", route -> route.path("/wallet/**")
                        .filters(filter -> filter.stripPrefix(1))
                        .uri("lb://game-car-racing-wallet"))

                .build();
    }
}
