package kr.co.MyPick_server.DTO.loginReigster;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.Date;

/**
 * RegisterReq is a Data Transfer Object used for handling user registration data.
 * It ensures that all user-provided data adheres to specified validation constraints,
 * making it robust against invalid or malicious inputs.
 */
@Data
public class RegisterReq {

    /**
     * User ID which must be unique and adhere to specific length and content requirements.
     */
    @NotBlank(message = "ID cannot be empty.")
    @Size(min = 8, max = 30, message = "ID must be between 8 and 30 characters.")
    private String id;

    /**
     * User password which needs to meet complexity requirements to enhance security.
     */
    @NotBlank(message = "Password cannot be empty.")
    @Size(min = 8, max = 30, message = "Password must be between 8 and 30 characters.")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,30}$",
            message = "Password must include at least one letter (lowercase or uppercase), one number, and one special character.")
    private String pw;

    /**
     * User's full name, ensuring it only contains letters and spaces to prevent scripting attacks or improper inputs.
     */
    @NotBlank(message = "Name cannot be empty.")
    @Pattern(regexp = "^[가-힣a-zA-Z ]+$", message = "Name must contain only letters and spaces.")
    @Size(max = 50, message = "Name must not exceed 50 characters.")
    private String name;

    /**
     * User's nickname which is often used in the application instead of the real name for display purposes.
     */
    @NotBlank(message = "NickName cannot be empty.")
    @Pattern(regexp = "^[가-힣a-zA-Z ]+$", message = "NickName must contain only letters.")
    private String nickName;

    /**
     * User's birth date which must be a past date to be valid, ensuring accurate age-related information.
     */
    @NotNull(message = "Birth date cannot be empty.")
    @Past(message = "Birth date must be a past date.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date birth;
}
