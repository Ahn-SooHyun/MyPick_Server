package kr.co.MyPick_server.Util;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * The BCryptService class provides methods for encrypting and verifying passwords using
 * Base64 encoding and BCrypt hashing.
 */
@Component
public class BCryptUtil {


    @Autowired
    Base64Util base64Util;

    /**
     * Encrypts the given password using Base64 encoding and BCrypt hashing.
     *
     * @param password The plain text password to be encrypted
     * @return The encrypted password as a BCrypt hash
     */
    // 비밀번호 암호화
    public String setPassword(String password) {
        String basePW = base64Util.encode(password);
        return BCrypt.hashpw(basePW, BCrypt.gensalt());
    }

    /**
     * Checks if the provided plain text password matches the stored hashed password.
     *
     * @param password The plain text password to be verified.
     * @param dbPassword The hashed password stored in the database.
     * @return true if the provided password matches the stored password, false otherwise.
     */
    //비밀번호 체크하기
    public boolean checkPassword(String password, String dbPassword) {
        String basePW = base64Util.encode(password);
        return BCrypt.checkpw(basePW, dbPassword);
    }
}
