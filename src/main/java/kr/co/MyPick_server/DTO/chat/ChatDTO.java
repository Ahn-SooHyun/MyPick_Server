package kr.co.MyPick_server.DTO.chat;

import lombok.Data;

import java.util.List;

/**
 * AI가 응답한 결과를 저장하는 DTO입니다.
 * - answer  : AI가 생성한 답변 텍스트 ( [답변] )
 * - summary : 답변의 요약 ( [요약] )
 * - list    : 답변 속 게임 목록 ( [리스트] )
 */
@Data
public class ChatDTO {
    private String answer;       // [답변]
    private String summary;      // [요약]
    private List<ListInfo> list; // [리스트]
}
