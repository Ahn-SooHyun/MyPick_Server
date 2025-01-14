package kr.co.MyPick_server.Service.chat;

import kr.co.MyPick_server.DTO.chat.ChatRoom.ChatRoomListDTO;

import java.util.List;

public interface RoomServiceImpl {

    List<ChatRoomListDTO> RoomListGet(int IDX);

    int RoomCreate(int IDX, String prompt, boolean chatCheck);

    int RoomDelete(int IDX, int roomIDX);

}