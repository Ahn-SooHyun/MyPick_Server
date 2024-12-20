package kr.co.MyPick_server.DTO;

import lombok.Data;

@Data
public class OpenAIReq {
    private String responseText; // 생성된 응답 텍스트
}