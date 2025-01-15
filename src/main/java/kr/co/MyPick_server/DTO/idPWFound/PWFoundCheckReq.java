package kr.co.MyPick_server.DTO.idPWFound;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * PWFoundCheckReq is a Data Transfer Object used for validating a user's identity by checking
 * a provided code against the user's stored details during the password recovery process.
 * This DTO helps ensure that requests to verify identity are both secure and meet defined requirements.
 */
@Data
public class PWFoundCheckReq {

    /**
     * User's ID that must be provided for identity verification. This ID is the unique identifier for the user
     * within the system, and it needs to meet specific size requirements to ensure consistency and security.
     */
    @NotBlank(message = "ID cannot be empty.")
    @Size(min = 8, max = 30, message = "ID must be between 8 and 30 characters.")
    private String id;

    /**
     * A recovery or verification code that has been previously issued to the user, typically via email or SMS,
     * as part of the password recovery process. This code must also adhere to certain length requirements
     * to ensure it is robust enough to prevent unauthorized access.
     */
    @NotBlank(message = "Code cannot be empty.")
    @Size(min = 8, max = 30, message = "Code must be between 8 and 30 characters.")
    private String code;

}
