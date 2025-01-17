package kr.co.MyPick_server.DTO.profile;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
public class UpdateProfileReq {

    @JsonProperty("CT_AT")  // Maps the field to the "CT_AT" key in JSON.
    @Pattern(
            regexp = "^[A-Za-z0-9_-]+=*\\.[A-Za-z0-9_-]+=*\\.[A-Za-z0-9_-]+=*$",
            message = "CT_AT field must be in a valid JWT format."
    )
    private String CT_AT;

    @NotNull(message = "Profile image is required.")
    private MultipartFile profileImage;
}
