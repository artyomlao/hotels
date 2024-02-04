package gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator gatewayRoutes(final RouteLocatorBuilder builder) {
        return builder.routes()
                .route("reception-service", r -> r.path("/reception/**")
                        .uri("lb://reception-service"))
                .route("users-service", r -> r.path("/users/**")
                        .uri("lb://users-service"))
                .route("auth-service", r -> r.path("/auth/**")
                        .uri("lb://auth-service"))
                .build();
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
