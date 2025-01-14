package kr.co.MyPick_server.DTO.chat.ChatMessageList;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ChatMessageListReq {
    @Pattern(
            regexp = "^[A-Za-z0-9-_]+\\.[A-Za-z0-9-_]+\\.[A-Za-z0-9-_]+$",
            message = "CT_AT 필드는 유효한 JWT 형식이어야 합니다."
    )
    private String CT_AT; // JWT 형식 검사

    @Min(value = 0, message = "chatIDX는 0 이상이어야 합니다.")
    private int chatIDX; // 0 이상 값

}