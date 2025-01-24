package kr.co.MyPick_server.DTO.profile;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdatePWReq {

    @JsonProperty("CT_AT")  // Maps the field to the "CT_AT" key in JSON.
    @Pattern(
            regexp = "^[A-Za-z0-9_-]+=*\\.[A-Za-z0-9_-]+=*\\.[A-Za-z0-9_-]+=*$",
            message = "CT_AT field must be in a valid JWT format."
    )
    private String CT_AT;

    @JsonProperty("oldPW")  // Maps the field to the "CT_AT" key in JSON.
    @NotBlank(message = "Old Password cannot be empty.")
    @Size(min = 8, max = 30, message = "Old Password must be between 8 and 30 characters.")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,30}$",
            message = "New Password must include at least one letter (lowercase or uppercase), one number, and one special character.")
    private String oldPW;

    @JsonProperty("newPW")  // Maps the field to the "CT_AT" key in JSON.
    @NotBlank(message = "New Password cannot be empty.")
    @Size(min = 8, max = 30, message = "New Password must be between 8 and 30 characters.")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,30}$",
            message = "New Password must include at least one letter (lowercase or uppercase), one number, and one special character.")
    private String newPW;

}
