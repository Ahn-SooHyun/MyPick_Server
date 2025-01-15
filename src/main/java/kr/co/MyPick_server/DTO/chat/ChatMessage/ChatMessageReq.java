package kr.co.MyPick_server.DTO.chat.ChatMessage;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * ChatMessageReq is a Data Transfer Object (DTO) used for sending chat messages.
 * It ensures that all necessary parameters such as user authentication token, chat room index,
 * and the message content itself meet specific validation criteria before being processed.
 */
@Data
public class ChatMessageReq {

    /**
     * CT_AT (Client Access Token) is used to authenticate the user making the request.
     * This field must contain a valid JSON Web Token (JWT), which is checked against a regular expression
     * to ensure its format matches the standard JWT structure. This validation is crucial for
     * securing chat operations and ensuring that the action is performed by an authenticated user.
     */
    @Pattern(
            regexp = "^[A-Za-z0-9-_]+\\.[A-Za-z0-9-_]+\\.[A-Za-z0-9-_]+$",
            message = "CT_AT field must be in a valid JWT format."
    )
    private String CT_AT; // JWT format verification

    /**
     * chatIDX represents the index of the chat room to which the message will be sent.
     * This field must be a non-negative integer to ensure it refers to a valid chat room.
     * The minimum value constraint prevents errors associated with invalid room indices.
     */
    @Min(value = 0, message = "chatIDX must be 0 or higher.")
    private int chatIDX; // Non-negative value validation for chat room index

    /**
     * prompt is the content of the message being sent. This field must not be blank,
     * ensuring that no empty messages are sent. It must contain at least one character,
     * supporting the integrity and usability of the chat service.
     */
    @NotBlank(message = "prompt must contain at least one character.")
    private String prompt; // Validation to ensure that the message content is not empty
}
