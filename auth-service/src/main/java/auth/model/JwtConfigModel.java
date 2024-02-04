package auth.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class JwtConfigModel {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.header}")
    private String authorizationHeader;

    @Value("${jwt.expiration}")
    private long validityInMilliseconds;
}
