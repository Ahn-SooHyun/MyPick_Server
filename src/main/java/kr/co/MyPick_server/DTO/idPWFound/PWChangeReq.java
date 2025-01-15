package kr.co.MyPick_server.DTO.idPWFound;

import jakarta.validation.constraints.*;
import lombok.Data;

/**
 * PWChangeReq is a Data Transfer Object used for password change requests.
 * It includes fields to verify the reset code and set a new password with robust validation
 * to ensure the new password adheres to security standards.
 */
@Data
public class PWChangeReq {

    /**
     * A unique code typically sent to the user via email or SMS to verify their identity
     * before allowing a password change. This code must meet specific length requirements
     * to ensure it is complex enough to prevent unauthorized access.
     */
    @NotBlank(message = "Code cannot be empty.")
    @Size(min = 8, max = 30, message = "Code must be between 8 and 30 characters.")
    private String code;

    /**
     * The new password that the user wants to set. It must follow strict complexity requirements,
     * including a mix of alphanumeric characters and special characters, to enhance account security.
     * This pattern ensures the password is strong enough to protect against common attack vectors such as brute force.
     */
    @NotBlank(message = "Password cannot be empty.")
    @Size(min = 8, max = 30, message = "Password must be between 8 and 30 characters.")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,30}$",
            message = "Password must include at least one letter (lowercase or uppercase), one number, and one special character.")
    private String pw;

}
