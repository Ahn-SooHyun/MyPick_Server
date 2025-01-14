package kr.co.MyPick_server.DTO.chat.ChatMessage;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ChatMessageReq {
    @Pattern(
            regexp = "^[A-Za-z0-9-_]+\\.[A-Za-z0-9-_]+\\.[A-Za-z0-9-_]+$",
            message = "CT_AT 필드는 유효한 JWT 형식이어야 합니다."
    )
    private String CT_AT; // JWT 형식 검사

    @Min(value = 0, message = "chatIDX는 0 이상이어야 합니다.")
    private int chatIDX; // 0 이상 값

    @NotBlank(message = "prompt는 최소 1글자 이상이어야 합니다.")
    private String prompt; // 1글자 이상 입력
}