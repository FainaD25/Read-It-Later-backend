package ee.taltech.iti03022024backend.security;

import ee.taltech.iti03022024backend.entities.user.UserEntity;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtGenerator {
    private final SecretKey key;

    public String generateToken(UserEntity userEntity) {
        return Jwts.builder()
                .subject(userEntity.getUsername())
                .claims(Map.of(
                        "userId", userEntity.getUserId() // Can add information here
                ))
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))  // 24 hours
                .signWith(key)
                .compact();
    }
}
