package kr.co.MyPick_server.DTO.chat;

import lombok.Data;

import java.util.Date;

@Data
public class ChatListDTO {
    private int chatIDX;
    private String category;
    private String Summary;
    private Date lastDate;
}
