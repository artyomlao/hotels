package gateway.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.experimental.UtilityClass;

import java.util.Date;

@UtilityClass
public final class JwtUtil {

    public void validateToken(final String token, final String secret) {
        final Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token);

        if (claimsJws.getBody()
                .getExpiration()
                .before(new Date())) {

            throw new RuntimeException("Token was expired");
        }
    }
}
