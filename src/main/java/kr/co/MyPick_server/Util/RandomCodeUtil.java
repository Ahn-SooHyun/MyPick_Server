package kr.co.MyPick_server.Util;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class RandomCodeUtil {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final int MIN_LENGTH = 8; // 최소 길이
    private static final int MAX_ADDITIONAL_LENGTH = 8; // 추가 길이 (0~7)

    public String getCode() {
        int length = MIN_LENGTH + RANDOM.nextInt(MAX_ADDITIONAL_LENGTH); // 8~15자리
        StringBuilder code = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            code.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }

        return code.toString();
    }
}
