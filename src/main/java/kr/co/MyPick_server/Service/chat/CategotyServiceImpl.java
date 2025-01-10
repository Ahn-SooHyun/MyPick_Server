package kr.co.MyPick_server.Service.chat;


import kr.co.MyPick_server.DTO.chat.CategoryChangeReq;

public interface CategotyServiceImpl {

    int ChatCreateRoom(int IDX, String prompt);
}
