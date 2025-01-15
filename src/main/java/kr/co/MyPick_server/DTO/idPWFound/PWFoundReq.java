package kr.co.MyPick_server.DTO.idPWFound;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * PWFoundReq is a Data Transfer Object used for processing requests to find or recover a user's password.
 * This involves verifying the user's identity through multiple fields including user ID, name, and birth date.
 */
@Data
public class PWFoundReq {

    /**
     * The unique identifier for the user. This ID is used to locate the user in the system and verify
     * that the subsequent details (name and birth date) match the records. It must be of adequate length
     * to ensure security and uniqueness within the system.
     */
    @NotBlank(message = "ID cannot be empty.")
    @Size(min = 8, max = 30, message = "ID must be between 8 and 30 characters.")
    private String id;

    /**
     * The full name of the user as registered in the system. This field is validated to ensure that it
     * only contains letters and spaces, accommodating names from various cultures without allowing
     * numeric or special characters that might be used in an attempt to inject malicious code.
     */
    @NotBlank(message = "Name cannot be empty.")
    @Pattern(regexp = "^[가-힣a-zA-Z ]+$", message = "Name must contain only letters and spaces.")
    private String name;

    /**
     * The birth date of the user, which is used as a part of the identity verification process.
     * It must be a date in the past, and it is formatted specifically to ensure consistency in
     * how date data is handled across different systems or components.
     */
    @NotNull(message = "Birth date cannot be empty.")
    @Past(message = "Birth date must be a past date.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birth;

}
