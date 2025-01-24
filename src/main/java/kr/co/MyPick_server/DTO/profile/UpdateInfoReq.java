package kr.co.MyPick_server.DTO.profile;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.Date;

@Data
public class UpdateInfoReq {

    @JsonProperty("CT_AT")  // Maps the field to the "CT_AT" key in JSON.
    @Pattern(
            regexp = "^[A-Za-z0-9_-]+=*\\.[A-Za-z0-9_-]+=*\\.[A-Za-z0-9_-]+=*$",
            message = "CT_AT field must be in a valid JWT format."
    )
    private String CT_AT;

    @NotBlank(message = "ID cannot be empty.")
    @Size(min = 8, max = 30, message = "ID must be between 8 and 30 characters.")
    private String id;

    @JsonProperty("nickName")
    @NotBlank(message = "NickName cannot be empty.")
    @Pattern(regexp = "^[가-힣a-zA-Z ]+$", message = "NickName must contain only letters.")
    private String nickName;
}
