package kr.co.MyPick_server.DTO.chat.ChatMessageList;

import lombok.Data;

@Data
public class ChatMessageListGetReq {

    private int userIDX;

    private int chatIDX; // 0 이상 값

}