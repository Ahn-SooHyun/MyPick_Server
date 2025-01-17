package kr.co.MyPick_server.DTO.profile;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.Date;

@Data
public class UpdateInfoUpdateReq {

    private int IDX;

    private String id;

    private String pw;

    private String name;

    private String nickName;

    private Date birth;
}
