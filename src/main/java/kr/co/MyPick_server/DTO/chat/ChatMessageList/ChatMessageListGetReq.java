package kr.co.MyPick_server.DTO.chat.ChatMessageList;

import lombok.Data;

/**
 * ChatMessageListGetReq is a Data Transfer Object (DTO) used for requesting a list of messages
 * from a specific chat room. This class helps in filtering and retrieving messages relevant
 * to a particular user and chat session, ensuring that users access only the content pertinent to them.
 */
@Data
public class ChatMessageListGetReq {

    /**
     * The unique identifier for the user making the request. This ID is used to ensure
     * that the message retrieval is personalized and secure, only fetching messages that
     * the user is authorized to view, based on their participation in the chat room.
     */
    private int userIDX;

    /**
     * The identifier of the chat room from which messages are to be retrieved. This ID is crucial
     * for fetching the correct set of messages associated with a specific chat session.
     * It must be a non-negative value, where typically, a value of zero could represent a default
     * or initial chat room setting, depending on the application's design.
     */
    private int chatIDX; // Must be a non-negative value indicating the chat room's ID

}
