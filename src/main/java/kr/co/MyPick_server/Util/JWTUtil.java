package kr.co.MyPick_server.Util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import kr.co.MyPick_server.DTO.JWT.JWTReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTUtil {

    @Autowired
    Base64Util base64Util;

    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;
    private static final long EXPIRATION_TIME = 86400000L; // 1일 (밀리초)

    public Map<String, Object> generateToken(JWTReq jwtReq) {
        // 매 호출마다 새로운 키 생성
        Key dynamicKey = Keys.secretKeyFor(SIGNATURE_ALGORITHM);

        String token = Jwts.builder()
                .setSubject(jwtReq.getId())
                .claim("name", jwtReq.getName())
                .claim("pw", jwtReq.getPw())
                .claim("birth", jwtReq.getBirth().toString())
                .claim("dateOfCreation", jwtReq.getDateOfCreation().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 만료 시간 추가
                .signWith(dynamicKey, SIGNATURE_ALGORITHM)
                .compact();

        // JWT와 매번 생성된 키를 Map에 담아 반환
        Map<String, Object> result = new HashMap<>();

        result.put("token", token);
        String encodedKey = base64Util.encode(String.valueOf(dynamicKey));
        result.put("key", encodedKey);

        return result;
    }

    public Claims validateAndGetClaims(String token, Key key) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            throw new IllegalArgumentException("Invalid JWT token", e);
        }
    }
}
