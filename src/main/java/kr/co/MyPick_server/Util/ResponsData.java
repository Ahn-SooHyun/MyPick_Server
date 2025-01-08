package kr.co.MyPick_server.Util;

import lombok.Data;

/**
 * The ResponsData class is a standardized structure for API response messages.
 * It includes a status code, a message describing the response, and an optional
 * data payload for additional information.
 *
 * This class uses Lombok's @Data annotation to automatically generate getters,
 * setters, and other utility methods.
 */
@Data
public class ResponsData {

    private String code; // Response status code ("200" for success, "500" for failure).
    private String message; // Descriptive message about the response (e.g., error details).

    private Object data; // Optional payload containing the response data.

    /**
     * Default constructor initializes the response object with default success values.
     * - Code is set to "200", indicating a successful response.
     * - Message is set to "success".
     */
    public ResponsData() {
        this.code = "200"; // Default success code.
        this.message = "success"; // Default success message.
    }
}
