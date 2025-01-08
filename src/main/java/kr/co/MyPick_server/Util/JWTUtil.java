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

    /**
     * Initializes the secret key for signing JWTs. If the key file exists, it loads the key from the file.
     * Otherwise, it generates a new key, saves it to the file, and sets it as the signing key.
     */
    @PostConstruct
    public void initializeSecretKey() {
        try {
            Path path = Paths.get(KEY_FILE_PATH);

            if (Files.exists(path)) {
                // Load key from file
                String keyString = new String(Files.readAllBytes(path)).trim();
                byte[] keyBytes = Base64.getDecoder().decode(keyString);
                fixedKey = new SecretKeySpec(keyBytes, SIGNATURE_ALGORITHM.getJcaName());
                logger.info("Secret key successfully loaded from file: {}", KEY_FILE_PATH);
            } else {
                // Generate new key and save to file
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

    /**
     * Generates a new random secret key for JWT signing.
     *
     * @return A 256-bit secret key as a byte array.
     */
    private byte[] generateSecretKey() {
        byte[] keyBytes = new byte[32]; // 256-bit key
        new java.security.SecureRandom().nextBytes(keyBytes);
        return keyBytes;
    }

    /**
     * Generates a JWT token using the provided user information.
     *
     * @param jwtReq The user information needed to generate the JWT.
     * @return A map containing the generated JWT and its signature.
     */
    public Map<String, Object> generateToken(JWTReq jwtReq) {
        String JWT = Jwts.builder()
                .setSubject(jwtReq.getId())
                .claim("name", jwtReq.getName())
                .claim("pw", jwtReq.getPw())
                .claim("birth", jwtReq.getBirth().toString())
                .claim("dateOfCreation", jwtReq.getDateOfCreation().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Set expiration time
                .signWith(fixedKey, SIGNATURE_ALGORITHM)
                .compact();

        String[] JWTParts = JWT.split("\\.");
        String signature = JWTParts[2]; // Extract signature part

        Map<String, Object> result = new HashMap<>();
        result.put("JWT", JWT);
        result.put("signature", signature);

        return result;
    }

    /**
     * Extracts the signature part of the JWT token.
     *
     * @param JWT The JWT token as a string.
     * @return The extracted signature part of the JWT.
     */
    public String extractKey(String JWT) {
        try {
            String[] JWTParts = JWT.split("\\.");
            if (JWTParts.length != 3) {
                throw new IllegalArgumentException("Invalid JWT structure");
            }
            return JWTParts[2]; // Return the signature part
        } catch (Exception e) {
            logger.error("Failed to extract key from JWT: {}", JWT, e);
            throw new IllegalArgumentException("Failed to extract key from JWT", e);
        }
    }
}
