package kr.co.MyPick_server.DTO.MongoDB;

import kr.co.MyPick_server.DTO.chat.ChatMessage.ChatMessageListInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Chat_List") // MongoDB 컬렉션 이름 설정
public class ChatMessageMongoReq {

    @Id
    private String id;            // MongoDB 문서의 고유 _id
    private int userIdx; // 유저 IDX
    private int chatIdx; // 채팅 방 IDX
    private LocalDateTime date; // 값이 추가된 날짜 시간
    private String question; // 질문 값
    private String answer; // 답변 값
    private String summary;
    private List<ChatMessageListInfoDTO> list;

}
