package kr.co.MyPick_server.Util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import kr.co.MyPick_server.DTO.JWT.JWTReq;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JWTUtil {

    private static final String SECRET_KEY = "MyPickSuperSecretKey1234567890"; // 256-bit 키
    private static final long EXPIRATION_TIME = 86400000; // 1일 (밀리초)

    private final Key key;

    public JWTUtil() {
        this.key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String generateToken(JWTReq jwtReq) {
        return Jwts.builder()
                .setSubject(jwtReq.getId())
                .claim("name", jwtReq.getName())
                .claim("pw", jwtReq.getPw())
                .claim("birth", jwtReq.getBirth().toString())
                .claim("dateOfCreation", jwtReq.getDateOfCreation().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }



    // JWT 검증 및 데이터 추출
    public Claims validateAndGetClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            throw new IllegalArgumentException("Invalid JWT token");
        }
    }
}
