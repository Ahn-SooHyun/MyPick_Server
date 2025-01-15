package kr.co.MyPick_server.DTO.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.sql.Timestamp;

/**
 * UseraccountSuspensionSetReq is a Data Transfer Object (DTO) used for updating
 * the suspension status of a user's account. This class encapsulates the necessary
 * information, such as the user ID and the suspension date, to process the suspension operation.
 */
@Data
public class UseraccountSuspensionSetReq {

    /**
     * The unique identifier (ID) of the user whose account suspension status is being updated.
     * This field is critical for identifying the user in the database.
     */
    private int IDX;

    /**
     * The suspension date and time for the user's account.
     * This field specifies when the account should be suspended or reactivated.
     *
     * Validation:
     * - Must not be null to ensure proper scheduling.
     * - Typically should represent a present or future date to prevent illogical suspension timings.
     *
     * JSON Mapping: "accountSuspension"
     */
    @JsonProperty("accountSuspension")  // Maps this field to "accountSuspension" in the JSON request.
    private Timestamp accountSuspension;
}
