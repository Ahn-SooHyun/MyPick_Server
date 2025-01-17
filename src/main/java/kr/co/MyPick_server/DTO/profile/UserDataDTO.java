package kr.co.MyPick_server.DTO.profile;

import lombok.Data;

import java.sql.Date;

@Data
public class UserDataDTO {
    private String id;
    private String name;
    private String nickName;
    private String profileImage; // Base64 인코딩된 이미지 데이터를 저장
    private Date birth;
}
