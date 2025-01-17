package kr.co.MyPick_server.Service.chat;

import kr.co.MyPick_server.DTO.MongoDB.ChatMessageMongoReq;
import kr.co.MyPick_server.DTO.chat.ChatMessage.ChatMessageDTO;
import kr.co.MyPick_server.DTO.chat.ChatMessageList.ChatMessageListGetReq;
import kr.co.MyPick_server.DTO.chat.ChatRoom.ChatRoomListDTO;

import java.util.List;

/**
 * The MessageServiceImpl interface defines the operations for managing chat functionalities
 * such as retrieving chat rooms, fetching chat messages, creating chat rooms, and sending chat messages.
 */
public interface MessageServiceImpl {

    /**
     * Retrieves a list of chat rooms available for a specific user identified by IDX.
     *
     * @param IDX The user index used to identify the user.
     * @return A list of ChatRoomListDTO representing the chat rooms available to the user.
     */
    List<ChatRoomListDTO> ChatListGet(int IDX);

    /**
     * Retrieves a list of chat messages for a given chat room.
     *
     * @param chatingListGetReq The request object containing information about the chat room.
     * @return A list of ChatMessageMongoReq representing the messages in the specified chat room.
     */
    List<ChatMessageMongoReq> ChatListGet(ChatMessageListGetReq chatingListGetReq);

    /**
     * Creates a new chat room based on the given prompt and user index.
     *
     * @param IDX The user index for whom the room is being created.
     * @param prompt A command or identifier for the type of chat room to be created.
     * @param chatCheck A boolean indicating whether the creation is conditional.
     * @return An integer representing the success status or room index.
     */
    int ChatCreateRoom(int IDX, String prompt, boolean chatCheck);

    /**
     * Sends a chat message to a specified room.
     *
     * @param IDX The user index of the message sender.
     * @param roomIDX The index of the chat room where the message is to be sent.
     * @param prompt The content of the message to send.
     * @return A ChatMessageDTO containing the details of the sent message.
     */
    ChatMessageDTO chatMessageSend(int IDX, int roomIDX, String prompt);

    int ChatRoomCheck(ChatMessageListGetReq chatMessageListGetReq);

}
