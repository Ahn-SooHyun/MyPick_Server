package kr.co.MyPick_server.DTO.loginReigster;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * LoginReq represents the login request data containing the user's ID and password.
 * This class is used to transfer login credentials from the client to the server,
 * ensuring that the input adheres to specified security and formatting rules.
 */
@Data
public class LoginReq {

    /**
     * The ID of the user attempting to log in.
     * Validation ensures the ID is neither null nor blank, and adheres to a specified length requirement,
     * promoting better security practices and preventing common input-related vulnerabilities.
     */
    @NotBlank(message = "ID cannot be empty.")
    @Size(min = 8, max = 30, message = "ID must be between 8 and 30 characters.")
    private String id;

    /**
     * The password of the user attempting to log in.
     * It includes multiple layers of validation:
     * - Non-blank: Ensures no empty values.
     * - Size: Restricts password length to between 8 and 30 characters to balance security and usability.
     * - Pattern: Ensures the password includes a mix of letters, numbers, and special characters to
     *   enhance password strength against brute-force attacks.
     */
    @NotBlank(message = "Password cannot be empty.")
    @Size(min = 8, max = 30, message = "Password must be between 8 and 30 characters.")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,30}$",
            message = "Password must include at least one letter (lowercase or uppercase), one number, and one special character.")
    private String pw;

}
