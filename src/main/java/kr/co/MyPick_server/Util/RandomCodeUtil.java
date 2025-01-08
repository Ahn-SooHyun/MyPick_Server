package kr.co.MyPick_server.Util;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class RandomCodeUtil {

    // Character set for generating the random code
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";

    // Secure random generator for cryptographic security
    private static final SecureRandom RANDOM = new SecureRandom();

    // Minimum length of the generated code
    private static final int MIN_LENGTH = 8;

    // Maximum additional length to add to the minimum length (final length: 8-15)
    private static final int MAX_ADDITIONAL_LENGTH = 8;

    /**
     * Generates a secure random code with a length between 8 and 15 characters.
     *
     * @return A randomly generated code consisting of alphanumeric and special characters.
     */
    public String getCode() {
        // Determine the length of the random code (between 8 and 15)
        int length = MIN_LENGTH + RANDOM.nextInt(MAX_ADDITIONAL_LENGTH);

        // StringBuilder for constructing the random code
        StringBuilder code = new StringBuilder(length);

        // Generate the code by appending random characters from the character set
        for (int i = 0; i < length; i++) {
            code.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }

        // Return the generated code as a string
        return code.toString();
    }
}
