package kr.co.MyPick_server.DTO.idPWFound;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * IDFoundReq is a Data Transfer Object used specifically for the process of finding a user's ID
 * based on personal details provided by the user. This typically includes their name and birth date.
 * This class ensures that all necessary validations are in place to protect against invalid or malicious inputs.
 */
@Data
public class IDFoundReq {

    /**
     * User's name as it would be provided during initial registration or profile setup.
     * It is validated to ensure that it contains only letters and spaces, catering to both
     * Western and Korean names.
     */
    @NotBlank(message = "Name cannot be empty.")
    @Pattern(regexp = "^[가-힣a-zA-Z ]+$", message = "Name must contain only letters and spaces.")
    private String name;

    /**
     * User's birth date which is used to further validate the identity of the user requesting ID recovery.
     * This field is strictly checked to be a past date to ensure logical validity.
     */
    @NotNull(message = "Birth date cannot be empty.")
    @Past(message = "Birth date must be a past date.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birth;

}
