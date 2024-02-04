package gateway.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.experimental.UtilityClass;

import java.security.Key;
import java.util.Date;

@UtilityClass
public final class JwtUtil {

    public void validateToken(final String token, final String secret) {
        final Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(getSignKey(secret))
                .build()
                .parseClaimsJws(token);

        if (!claimsJws.getBody()
                .getExpiration()
                .before(new Date())) {

            throw new RuntimeException("Token was expired");
        }
    }

    private Key getSignKey(final String secret) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
