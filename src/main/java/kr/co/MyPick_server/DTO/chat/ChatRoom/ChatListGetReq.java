package kr.co.MyPick_server.DTO.chat.ChatRoom;

import lombok.Data;

@Data
public class ChatListGetReq {
    private int IDX;
    private int chatIDX; // 0 이상 값
}
