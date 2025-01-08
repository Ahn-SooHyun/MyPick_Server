package kr.co.MyPick_server.Util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import kr.co.MyPick_server.DTO.JWT.JWTReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTUtil {

    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;
    private static final long EXPIRATION_TIME = 86400000L; // 1일 (밀리초)
    private static final String KEY_FILE_PATH = "src/main/java/kr/co/MyPick_server/config/dynamicKey.txt"; // 키 파일 경로

    private Key fixedKey;

    private final Logger logger = LoggerFactory.getLogger(JWTUtil.class);

    // 서버 시작 시 고정 키 생성
    @PostConstruct
    public void initializeSecretKey() {
        try {
            Path path = Paths.get(KEY_FILE_PATH);

            if (Files.exists(path)) {
                // 파일에서 키 읽기
                String keyString = new String(Files.readAllBytes(path)).trim();
                byte[] keyBytes = Base64.getDecoder().decode(keyString);
                fixedKey = new SecretKeySpec(keyBytes, SIGNATURE_ALGORITHM.getJcaName());
                logger.info("Secret key successfully loaded from file: {}", KEY_FILE_PATH);
            } else {
                // 파일 생성 및 키 저장
                byte[] keyBytes = generateSecretKey();
                String keyString = Base64.getEncoder().encodeToString(keyBytes);
                Files.write(path, keyString.getBytes(), StandardOpenOption.CREATE_NEW);
                fixedKey = new SecretKeySpec(keyBytes, SIGNATURE_ALGORITHM.getJcaName());
                logger.info("Secret key successfully generated and saved to file: {}", KEY_FILE_PATH);
            }
        } catch (IOException e) {
            logger.error("Failed to load or create secret key file: {}", e.getMessage());
            throw new RuntimeException("Unable to initialize secret key", e);
        }
    }

    private byte[] generateSecretKey() {
        // 새로운 키 생성
        byte[] keyBytes = new byte[32]; // 256비트
        new java.security.SecureRandom().nextBytes(keyBytes);
        return keyBytes;
    }

    public Map<String, Object> generateToken(JWTReq jwtReq) {
        // JWT 생성
        String JWT = Jwts.builder()
                .setSubject(jwtReq.getId())
                .claim("name", jwtReq.getName())
                .claim("pw", jwtReq.getPw())
                .claim("birth", jwtReq.getBirth().toString())
                .claim("dateOfCreation", jwtReq.getDateOfCreation().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 만료 시간 추가
                .signWith(fixedKey, SIGNATURE_ALGORITHM)
                .compact();

        // 토큰의 3번째 부분(서명) 추출
        String[] JWTParts = JWT.split("\\.");
        String signature = JWTParts[2];

        // 결과 반환
        Map<String, Object> result = new HashMap<>();
        result.put("JWT", JWT);
        result.put("signature", signature);

        return result;
    }

    public String extractKey(String JWT) {
        try {
            // JWT에서 header를 추출하고 key 복원
            String[] JWTParts = JWT.split("\\.");
            if (JWTParts.length != 3) {
                throw new IllegalArgumentException("Invalid JWT structure");
            }

            // JWT 헤더와 payload는 Base64로 인코딩된 상태이므로 디코딩
            String keyPart = JWTParts[2]; // 서명 부분 추출
            return keyPart;
        } catch (Exception e) {
            logger.error("Failed to extract key from JWT: {}", JWT, e);
            throw new IllegalArgumentException("Failed to extract key from JWT", e);
        }
    }
}
