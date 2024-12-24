package kr.co.MyPick_server.DTO.loginReigster;

import lombok.Data;

import java.util.Date;

@Data
public class AutoLoginCheckReq {
    private int userIdx;
    private Date loginTokenDate;

}
