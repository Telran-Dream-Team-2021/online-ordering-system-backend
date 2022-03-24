package telran.oos.security;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class JwtUtils {
    @Value("${app.jwt.exp.period: 3600000}")
    long expTimePeriodMs;
    @Value("${app.jwt.secret}")
    String secret;

    public String create(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(getExpDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    private Date getExpDate() {
        return new Date(new Date().getTime() + expTimePeriodMs);
    }

    public String validate(String jwt) {
        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(jwt).getBody().getSubject();
        } catch (ExpiredJwtException e) {
            log.error("token expired");
        } catch (UnsupportedJwtException | MalformedJwtException e) {
            log.error("token malformed");
        } catch (SignatureException e) {
            log.error("wrong token signature");
        } catch (IllegalArgumentException e) {
            log.error("empty token");
        }

        return null;
    }
}
