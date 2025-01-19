package kr.co.MyPick_server.DTO.admin;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserRoomList {
    private int chatIDX;
    private String category;
    private String summary;
    private Timestamp lastDate;
}
