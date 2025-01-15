package kr.co.MyPick_server.DTO.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.sql.Timestamp;

/**
 * UseraccountSuspensionReq is a Data Transfer Object (DTO) used for handling account suspension requests.
 * This class encapsulates the necessary data for suspending or reactivating a user's account,
 * including authentication and user identification details.
 */
@Data
public class UseraccountSuspensionReq {

    /**
     * CT_AT (Client Access Token) is a JSON Web Token (JWT) used to authenticate the user making the request.
     * This field ensures that only authorized users can perform account suspension operations.
     *
     * Validation:
     * - The token must match the standard JWT format (three base64-url encoded strings separated by dots).
     *
     * JSON Mapping: "CT_AT"
     */
    @JsonProperty("CT_AT")  // Maps this field to the "CT_AT" key in the JSON request body.
    @Pattern(
            regexp = "^[A-Za-z0-9_-]+=*\\.[A-Za-z0-9_-]+=*\\.[A-Za-z0-9_-]+=*$",
            message = "CT_AT field must be in a valid JWT format."
    )
    private String CT_AT;

    /**
     * The unique identifier (ID) of the user whose account suspension status is being updated.
     * This field is required to ensure the request targets the correct user.
     *
     * Validation:
     * - Must not be empty.
     * - Must be between 8 and 30 characters.
     */
    @NotBlank(message = "ID cannot be empty.")
    @Size(min = 8, max = 30, message = "ID must be between 8 and 30 characters.")
    private String id;

    /**
     * The name of the user whose account suspension status is being updated.
     * This field helps identify the user and adds a layer of verification to the request.
     *
     * Validation:
     * - Must not be empty.
     * - Must only contain letters and spaces (Korean, English).
     * - Cannot exceed 50 characters in length.
     */
    @NotBlank(message = "Name cannot be empty.")
    @Pattern(regexp = "^[가-힣a-zA-Z ]+$", message = "Name must contain only letters and spaces.")
    @Size(max = 50, message = "Name must not exceed 50 characters.")
    private String name;

    /**
     * The account suspension date, which indicates when the user's account should be suspended.
     * This can also be used to reactivate an account by setting a future date for reactivation.
     *
     * Validation:
     * - Must not be null.
     * - Must be a present or future timestamp (past dates are not allowed).
     *
     * JSON Mapping: "accountSuspension"
     */
    @JsonProperty("accountSuspension")  // Maps this field to the "accountSuspension" key in the JSON request body.
    @NotNull(message = "Account suspension date cannot be null.")
    @FutureOrPresent(message = "Account suspension date must be in the present or future.")
    private Timestamp accountSuspension;
}
