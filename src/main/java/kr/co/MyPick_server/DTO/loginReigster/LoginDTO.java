package kr.co.MyPick_server.DTO.loginReigster;

import lombok.Data;

import java.util.UUID;

@Data
public class LoginDTO {
    private UUID tocken;
    private String CT_AT;

}
