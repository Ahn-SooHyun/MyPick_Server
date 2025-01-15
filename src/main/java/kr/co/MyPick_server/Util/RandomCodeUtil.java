package kr.co.MyPick_server.Util;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

/**
 * RandomCodeUtil is a utility class designed to generate secure random codes.
 * These codes can be used for features such as verification codes, temporary passwords,
 * or any other scenario that requires a random, difficult-to-guess string.
 */
@Component
public class RandomCodeUtil {

    // Defines the character set to be used in generating the random code.
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";

    // Secure random generator for cryptographic security.
    private static final SecureRandom RANDOM = new SecureRandom();

    // Minimum length of the generated code.
    private static final int MIN_LENGTH = 8;

    // Maximum additional length to add to the minimum length (final length: 8-15).
    private static final int MAX_ADDITIONAL_LENGTH = 8;

    /**
     * Generates a secure random code with a length between 8 and 15 characters.
     * This method ensures that the code is sufficiently complex and secure by
     * incorporating upper and lower case letters, numbers, and special characters.
     *
     * @return A randomly generated code consisting of alphanumeric and special characters.
     */
    public String getCode() {
        // Calculate the actual length of the code to be generated.
        int length = MIN_LENGTH + RANDOM.nextInt(MAX_ADDITIONAL_LENGTH);

        // Use StringBuilder for efficient character concatenation.
        StringBuilder code = new StringBuilder(length);

        // Generate the code by selecting random characters from the defined character set.
        for (int i = 0; i < length; i++) {
            code.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }

        // Return the constructed code.
        return code.toString();
    }
}
