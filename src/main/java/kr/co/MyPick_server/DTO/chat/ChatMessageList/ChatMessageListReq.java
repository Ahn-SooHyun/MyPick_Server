package kr.co.MyPick_server.DTO.chat.ChatMessageList;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * ChatMessageListReq is a Data Transfer Object (DTO) designed for retrieving messages from a specific chat room.
 * It includes security validations to ensure that the requestor has a valid JWT token and specifies the chat room
 * by its index, which must be a non-negative number to align with system database requirements.
 */
@Data
public class ChatMessageListReq {
    /**
     * CT_AT (Client Token or Access Token) represents the JWT token required for authenticating the request.
     * This token ensures that the request to access chat messages comes from an authenticated source. The pattern
     * validation checks that the token format adheres to standard JWT structures, enhancing security by
     * verifying the token's structural integrity before processing the request.
     */
    @Pattern(
            regexp = "^[A-Za-z0-9-_]+\\.[A-Za-z0-9-_]+\\.[A-Za-z0-9-_]+$",
            message = "CT_AT must be in a valid JWT format."
    )
    private String CT_AT;

    /**
     * chatIDX represents the index of the chat room from which messages are being requested.
     * It is validated to be a non-negative integer, ensuring that the request points to a valid
     * or potentially valid chat room within the system's architecture. This validation prevents
     * errors related to negative indexing in databases or systems that do not accept such values.
     */
    @Min(value = 0, message = "chatIDX must be 0 or higher.")
    private int chatIDX;

}
