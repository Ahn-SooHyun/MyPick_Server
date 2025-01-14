package kr.co.MyPick_server.DTO.JWT;

import lombok.Data;

import java.util.Date;


@Data
public class JWTReq {
    private int admin;
    private String id;
    private String pw;
    private String name;
    private Date birth;
    private Date dateOfCreation;
}
