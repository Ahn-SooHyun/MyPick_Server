package kr.co.MyPick_server.DTO.chat;

import lombok.Data;

@Data
public class ChatReq {
    private String CT_AT;
    private int chatIDX ;
    private String prompt; // 요청에 포함될 질문 또는 입력값
}