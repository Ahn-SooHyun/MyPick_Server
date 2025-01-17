package kr.co.MyPick_server.DAO.Chat;

import kr.co.MyPick_server.DTO.MongoDB.ChatMessageMongoReq;
import kr.co.MyPick_server.DTO.chat.ChatRoom.ChatRoomListDTO;
import kr.co.MyPick_server.DTO.chat.ChatRoom.ChatRoomCreateReq;
import kr.co.MyPick_server.DTO.chat.ChatMessageList.ChatMessageListGetReq;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * ChatDAO is a Data Access Object (DAO) interface for managing chat room and message-related operations
 * in the application's relational database. It provides methods to retrieve, create, update, and delete
 * chat rooms and their associated data.
 */
@Mapper
public interface ChatDAO {

    /**
     * Retrieves a list of chat rooms associated with a specific user.
     *
     * @param IDX The unique identifier of the user whose chat rooms are to be fetched.
     * @return A list of `ChatRoomListDTO` objects representing the user's chat rooms.
     */
    List<ChatRoomListDTO> ChatRoomListGet(int IDX);

    /**
     * Creates a new chat room in the database.
     *
     * @param ChatRoomCreateReq A DTO containing the information required to create a chat room,
     *                          including the user's ID and the category or type of the chat room.
     */
    void ChatRoomCreate(ChatRoomCreateReq ChatRoomCreateReq);

    /**
     * Retrieves the last (most recent) chat room index for a specific user.
     *
     * @param IDX The unique identifier of the user.
     * @return The index of the last chat room created by the user.
     */
    int ChatRoomLastIDXGet(int IDX);

    /**
     * Fetches the category or type of a specific chat room.
     *
     * @param chatIDX The unique identifier of the chat room.
     * @return A string representing the category of the chat room (e.g., "general", "support").
     */
    String ChatRoomCategoryGet(int chatIDX);

    /**
     * Retrieves the index of a chat room based on specific criteria.
     *
     * @param chatMessageListGetReq A DTO containing criteria for identifying the chat room,
     *                              such as user ID and chat room parameters.
     * @return The index of the chat room that matches the provided criteria, or null if no match is found.
     */
    Integer ChatRoomIDXGet(ChatMessageListGetReq chatMessageListGetReq);

    /**
     * Updates the information of a chat room, such as its last activity or message details.
     *
     * @param chatMessageMongoReq A DTO containing the updated chat room information,
     *                            including message and timestamp data.
     */
    void ChatRoomUpdate(ChatMessageMongoReq chatMessageMongoReq);

    /**
     * Deletes a specific chat room from the database based on provided criteria.
     *
     * @param chatMessageListGetReq A DTO containing criteria for identifying the chat room to delete,
     *                              such as user ID and chat room index.
     * @return Returns 1 if the deletion is successful, otherwise returns 0.
     */
    int ChatRoomDelete(ChatMessageListGetReq chatMessageListGetReq);

    Integer ChatRoomCheck(ChatMessageListGetReq chatMessageListGetReq);

}
