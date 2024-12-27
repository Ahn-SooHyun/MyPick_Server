package kr.co.MyPick_server.DTO.loginReigster;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.Date;

@Data
public class RegisterReq {

    @NotBlank(message = "ID cannot be empty.")
    @Size(min = 8, max = 30, message = "ID must be between 8 and 30 characters.")
    private String id;

    @NotBlank(message = "Password cannot be empty.")
    @Size(min = 8, max = 30, message = "Password must be between 8 and 30 characters.")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,30}$",
            message = "Password must include at least one letter (lowercase or uppercase), one number, and one special character.")
    private String pw;

    @NotBlank(message = "Name cannot be empty.")
    @Pattern(regexp = "^[가-힣a-zA-Z ]+$", message = "Name must contain only letters and spaces.")
    private String name;

    @NotBlank(message = "NickName cannot be empty.")
    @Pattern(regexp = "^[가-힣a-zA-Z ]+$", message = "NickName must contain only letters.")
    private String nickName;

    @NotNull(message = "Birth date cannot be empty.")
    @Past(message = "Birth date must be a past date.")
    private Date birth;
}
