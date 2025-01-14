package kr.co.MyPick_server.DTO.loginReigster;

import lombok.Data;

import java.util.UUID;

@Data
public class LoginUpdateRes {
    private int IDX;
    private String tocken;
    private String JWTKey;
    private String General;
}
