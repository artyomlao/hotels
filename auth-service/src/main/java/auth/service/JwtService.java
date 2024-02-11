package auth.service;

import auth.model.JwtConfigModel;
import auth.model.exception.JwtAuthenticationException;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    private final JwtConfigModel model;

    public JwtService(final JwtConfigModel model) {
        this.model = model;
    }

    public String generateToken(final String username) {
        final Map<String, Object> claims = new HashMap<>();
        final Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + model.getValidityInMilliseconds() * 1000))
                .signWith(SignatureAlgorithm.HS512, model.getSecretKey())
                .compact();
    }

    public boolean validateToken(final HttpServletRequest request) throws JwtAuthenticationException {
        try {
            final String header = request.getHeader(model.getAuthorizationHeader());

            final Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(model.getSecretKey())
                    .build()
                    .parseClaimsJws(header);

            return !claimsJws.getBody()
                    .getExpiration()
                    .before(new Date());
        } catch (final JwtException | IllegalArgumentException e) {
            throw new JwtAuthenticationException("JWT token is incorrect or expired", HttpStatus.FORBIDDEN);
        }
    }
}
