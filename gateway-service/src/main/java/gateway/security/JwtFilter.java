package gateway.security;

import gateway.model.JwtConfigModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

@Component
public class JwtFilter extends GenericFilterBean {

    private final JwtConfigModel config;

    public JwtFilter(final JwtConfigModel config) {
        this.config = config;
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) {
        final String token = (((HttpServletRequest) (request)).getHeader(config.getAuthorizationHeader()));

        final Claims claims = Jwts.parser()
                .setSigningKey(config.getSecretKey().getBytes())
                .parseClaimsJws(token)
                .getBody();

        final String username = claims.getSubject();

        if (username != null) {
            final UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null);

            SecurityContextHolder.getContext().setAuthentication(auth);
        }
    }
}
