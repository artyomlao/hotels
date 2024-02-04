package auth.service;

import auth.model.exception.JwtAuthenticationException;
import auth.model.JwtConfigModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.Key;
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

        final byte[] keyBytes = Decoders.BASE64.decode(model.getSecretKey());
        final Key key = Keys.hmacShaKeyFor(keyBytes);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + model.getValidityInMilliseconds() * 1000))
                .signWith(key)
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
