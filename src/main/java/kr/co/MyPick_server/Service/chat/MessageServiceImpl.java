package kr.co.MyPick_server.Service.chat;


import kr.co.MyPick_server.DTO.MongoDB.ChatMessageMongoReq;
import kr.co.MyPick_server.DTO.chat.ChatMessage.ChatMessageDTO;
import kr.co.MyPick_server.DTO.chat.ChatMessageList.ChatMessageListGetReq;
import kr.co.MyPick_server.DTO.chat.ChatRoom.ChatRoomListDTO;

import java.util.List;

public interface MessageServiceImpl {

    List<ChatRoomListDTO> ChatListGet(int IDX);

    List<ChatMessageMongoReq> ChatListGet(ChatMessageListGetReq chatingListGetReq );

    int ChatCreateRoom(int IDX, String prompt, boolean chatCheck);

    ChatMessageDTO chatMessageSend(int IDX, int roomIDX, String prompt);

}