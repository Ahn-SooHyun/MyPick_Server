package kr.co.MyPick_server.DTO.chat.ChatRoom;

import lombok.Data;

/**
 * ChatRoomCreateReq is a Data Transfer Object (DTO) used for creating new chat rooms
 * within an application. This class carries the necessary parameters required to specify
 * the properties and categorization of a new chat room.
 */
@Data
public class ChatRoomCreateReq {
    /**
     * IDX represents the identifier of the user who is requesting to create a new chat room.
     * This index is crucial for establishing ownership and permissions for the chat room,
     * ensuring that the room is associated with the correct user account and that proper
     * permissions are set based on the user's role and rights within the system.
     */
    private int IDX;

    /**
     * Category specifies the type or theme of the chat room to be created. This could include
     * labels such as 'support', 'general discussion', 'private', or any other categorization
     * that helps in organizing and managing chat rooms according to their purpose and intended use.
     * The category helps users and the system to filter and locate chat rooms more efficiently.
     */
    private String category;
}
