package gateway.filter;

import gateway.model.JwtConfigModel;
import gateway.util.JwtUtil;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private final JwtConfigModel configModel;

    public AuthenticationFilter(final JwtConfigModel configModel) {
        this.configModel = configModel;
    }

    @Override
    public GatewayFilter apply(final Config config) {
        return ((exchange, chain) -> {
            if (RouteValidator.isSecured.test(exchange.getRequest())) {
                final String headerValue =
                        Optional.ofNullable(
                                        exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION))
                                .map(strings -> strings.get(0))
                                .orElseThrow(() -> new RuntimeException("Missing authorization headerValue"));

                String token = null;

                if (headerValue != null && headerValue.startsWith("Bearer ")) {
                    token = headerValue.substring(7);
                }

                try {
                    JwtUtil.validateToken(token, configModel.getSecretKey());
                } catch (final Exception e) {
                    throw new RuntimeException("Error while validating token");
                }
            }
            return chain.filter(exchange);
        });
    }

    public static class Config {

    }
}
