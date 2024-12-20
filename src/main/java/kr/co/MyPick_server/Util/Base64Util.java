package kr.co.MyPick_server.Util;

import java.util.Base64;

/**
 * The Base64Util class provides utility methods for encoding and decoding strings to and from Base64.
 */
public class Base64Util {

    /**
     * Encodes a string to Base64.
     *
     * @param data The string to encode
     * @return The encoded Base64 string
     */
    public static String encode(String data) {
        return Base64.getEncoder().encodeToString(data.getBytes());
    }

    /**
     * Decodes a Base64 encoded string.
     *
     * @param data The Base64 string to decode
     * @return The decoded string
     */
    public static String decode(String data) {
        byte[] decodedBytes = Base64.getDecoder().decode(data);
        return new String(decodedBytes);
    }

}
