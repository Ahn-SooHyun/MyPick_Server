package kr.co.MyPick_server.DTO;

import lombok.Data;

@Data
public class OpenAIRes {
    private String prompt; // 요청에 포함될 질문 또는 입력값
}