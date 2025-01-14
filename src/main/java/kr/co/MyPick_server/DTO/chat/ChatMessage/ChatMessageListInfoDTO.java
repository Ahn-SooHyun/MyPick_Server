package kr.co.MyPick_server.DTO.chat.ChatMessage;

import lombok.Data;

/**
 * 게임 정보를 저장하는 DTO입니다.
 * - title  : 게임 제목 (발매 연도 포함)
 * - artist : 제작사
 */
@Data
public class ChatMessageListInfoDTO {
    private String title;
    private String creator;
}
