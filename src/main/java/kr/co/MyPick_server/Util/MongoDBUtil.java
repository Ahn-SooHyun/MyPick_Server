package kr.co.MyPick_server.Util;

import org.springframework.stereotype.Component;

/**
 * MongoDBUtil provides utility methods for validating string and numeric data,
 * which can be used before inserting or updating documents in MongoDB.
 */
@Component
public class MongoDBUtil {

    /**
     * Checks whether a given string is valid. A valid string is one that is not null
     * and not empty (excluding leading or trailing whitespace).
     *
     * @param value The string to validate.
     * @return true if the string is neither null nor empty after trimming; false otherwise.
     */
    public boolean isValidString(String value) {
        return value != null && !value.trim().isEmpty();
    }

    /**
     * Checks whether a given number is valid. A valid number is one that is not null
     * and has a value of zero or greater.
     *
     * @param value The number to validate.
     * @return true if the number is not null and is zero or positive; false otherwise.
     */
    public boolean isValidNumber(Number value) {
        return value != null && value.doubleValue() >= 0;
    }
}
