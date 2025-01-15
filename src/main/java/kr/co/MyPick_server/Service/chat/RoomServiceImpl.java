package kr.co.MyPick_server.Service.chat;

import kr.co.MyPick_server.DTO.chat.ChatRoom.ChatRoomListDTO;

import java.util.List;

/**
 * The RoomServiceImpl interface outlines the core functionalities required for managing chat rooms
 * within the application. This includes retrieving, creating, and deleting chat rooms.
 */
public interface RoomServiceImpl {

    /**
     * Retrieves a list of all chat rooms available to a specific user, identified by their user index (IDX).
     *
     * @param IDX The user index that uniquely identifies the user within the system.
     * @return A list of ChatRoomListDTO, each representing a chat room available to the user.
     */
    List<ChatRoomListDTO> RoomListGet(int IDX);

    /**
     * Creates a new chat room for the user with the specified IDX. The nature and type of the chat room
     * are determined by the provided prompt and whether the room creation is conditional based on the chatCheck flag.
     *
     * @param IDX The user index for whom the room is being created.
     * @param prompt A string that could be a command or identifier determining the room's characteristics or theme.
     * @param chatCheck A boolean value indicating if the room should only be created under specific conditions.
     * @return An integer indicating the success of the operation or the index of the newly created chat room.
     */
    int RoomCreate(int IDX, String prompt, boolean chatCheck);

    /**
     * Deletes a specific chat room identified by roomIDX for a user specified by IDX.
     *
     * @param IDX The user index of the user who is attempting to delete the room.
     * @param roomIDX The index of the chat room to be deleted.
     * @return An integer indicating the success of the deletion operation. Typically, this could be a status code,
     * where 0 might mean failure and a positive value indicates successful deletion.
     */
    int RoomDelete(int IDX, int roomIDX);

}
