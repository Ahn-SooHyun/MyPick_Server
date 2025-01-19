package kr.co.MyPick_server.DTO.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * UserReq is a Data Transfer Object (DTO) used for handling user-related requests in administrative operations.
 * This class encapsulates necessary details such as authentication token, user ID, and name.
 */
@Data
public class UserMessageReq {

    /**
     * CT_AT (Client Access Token) is a JWT used for authenticating and authorizing the user making the request.
     * This field ensures that only authorized requests are processed.
     *
     * Validation:
     * - Must follow the standard JWT format, which consists of three base64-url encoded segments separated by dots.
     * - Ensures the integrity and validity of the token.
     *
     * JSON Mapping: The field is mapped to "CT_AT" in the JSON request body.
     */
    @JsonProperty("CT_AT")  // Maps the field to the "CT_AT" key in JSON.
    @Pattern(
            regexp = "^[A-Za-z0-9_-]+=*\\.[A-Za-z0-9_-]+=*\\.[A-Za-z0-9_-]+=*$",
            message = "CT_AT field must be in a valid JWT format."
    )
    private String CT_AT;

    /**
     * The unique identifier (ID) of the user.
     * This field is required to specify which user the operation should target.
     *
     * Validation:
     * - Must not be blank to ensure meaningful input.
     * - Must be between 8 and 30 characters to conform to system-defined ID length requirements.
     */
    @NotBlank(message = "ID cannot be empty.")
    @Size(min = 8, max = 30, message = "ID must be between 8 and 30 characters.")
    private String id;

    /**
     * The full name of the user.
     * This field is used for additional verification and ensures the request targets the correct user.
     *
     * Validation:
     * - Must not be blank to ensure that the name is provided.
     * - Must only contain letters (Korean or English) and spaces.
     * - Must not exceed 50 characters to prevent excessively long names.
     */
    @NotBlank(message = "Name cannot be empty.")
    @Pattern(
            regexp = "^[가-힣a-zA-Z ]+$",
            message = "Name must contain only letters and spaces."
    )
    @Size(max = 50, message = "Name must not exceed 50 characters.")
    private String name;

    @JsonProperty("chatIDX")
    @Min(value = 1, message = "chatIDX must be 1 or higher.")
    private int chatIDX;
}
