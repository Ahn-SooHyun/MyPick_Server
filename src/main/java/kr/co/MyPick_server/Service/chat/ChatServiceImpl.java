package kr.co.MyPick_server.Service.chat;


import kr.co.MyPick_server.DTO.chat.ChatReq;
import kr.co.MyPick_server.DTO.chat.ChatRes;

public interface ChatServiceImpl {
    ChatRes getResponse(ChatReq ChatReq);
}