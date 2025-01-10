package kr.co.MyPick_server.Service.chat;


import kr.co.MyPick_server.DTO.chat.ChatListDTO;
import kr.co.MyPick_server.DTO.chat.ChatReq;
import kr.co.MyPick_server.DTO.chat.ChatRes;

import java.util.List;

public interface ChatServiceImpl {

    List<ChatListDTO> ChatListGet(int IDX);

    ChatRes getResponse(int IDX, String prompt);
}