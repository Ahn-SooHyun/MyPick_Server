package kr.co.MyPick_server.DTO.chat.ChatRoom;

import lombok.Data;

import java.util.Date;

@Data
public class ChatRoomListDTO {
    private int chatIDX;
    private String category;
    private String Summary;
    private Date lastDate;
}
