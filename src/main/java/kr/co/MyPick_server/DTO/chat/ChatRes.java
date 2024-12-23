package kr.co.MyPick_server.DTO.chat;

import lombok.Data;

@Data
public class ChatRes {
    private String prompt; // 요청에 포함될 질문 또는 입력값
}