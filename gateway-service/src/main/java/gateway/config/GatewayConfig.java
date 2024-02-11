package gateway.config;

import gateway.filter.AuthenticationFilter;
import gateway.model.JwtConfigModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class GatewayConfig {

    private final JwtConfigModel model;

    @Autowired
    public GatewayConfig(final JwtConfigModel model) {
        this.model = model;
    }

    @Bean
    public RouteLocator gatewayRoutes(final RouteLocatorBuilder builder) {
        return builder.routes()
                .route("reception-service", r -> r.path("/reception/**")
                        .filters(config -> config.filter(
                                authenticationFilter().apply(new AuthenticationFilter.Config())))
                        .uri("lb://reception-service"))
                .route("users-service", r -> r.path("/users/**")
                        .filters(config -> config.filter(
                                authenticationFilter().apply(new AuthenticationFilter.Config())))
                        .uri("lb://users-service"))
                .route("auth-service", r -> r
                        .path("/auth/**")
                        .uri("lb://auth-service"))
                .build();
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public AuthenticationFilter authenticationFilter() {
        return new AuthenticationFilter(model);
    }
}
