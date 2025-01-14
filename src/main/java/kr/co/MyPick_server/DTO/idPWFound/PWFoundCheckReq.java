package kr.co.MyPick_server.DTO.idPWFound;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class PWFoundCheckReq {

    @NotBlank(message = "ID cannot be empty.")
    @Size(min = 8, max = 30, message = "ID must be between 8 and 30 characters.")
    private String id;

    @NotBlank(message = "Code cannot be empty.")
    @Size(min = 8, max = 30, message = "Code must be between 8 and 30 characters.")
    private String code;

}
