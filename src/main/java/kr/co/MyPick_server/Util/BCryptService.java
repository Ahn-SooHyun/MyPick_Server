package kr.co.MyPick_server.Util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * The BCryptService class provides methods for encrypting and verifying passwords using
 * Base64 encoding and BCrypt hashing.
 */
public class BCryptService {


    /**
     * Encrypts the given password using Base64 encoding and BCrypt hashing.
     *
     * @param password The plain text password to be encrypted
     * @return The encrypted password as a BCrypt hash
     */
    // 비밀번호 암호화
    public static String setPassword(String password) {
        Base64Util util = new Base64Util();
        String basePW = util.encode(password);
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
    public static boolean checkPassword(String password, String dbPassword) {
        Base64Util util = new Base64Util();
        String basePW = util.encode(password);
        return BCrypt.checkpw(basePW, dbPassword);
    }
}
