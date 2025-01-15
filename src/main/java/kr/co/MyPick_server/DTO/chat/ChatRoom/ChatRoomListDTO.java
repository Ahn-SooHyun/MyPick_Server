package kr.co.MyPick_server.DTO.chat.ChatRoom;

import lombok.Data;

import java.util.Date;

/**
 * ChatRoomListDTO is a Data Transfer Object (DTO) that provides essential information about chat rooms.
 * It is typically used to display a list of chat rooms to users, showing key attributes that help users
 * make decisions about which chat room to enter or view.
 */
@Data
public class ChatRoomListDTO {
    /**
     * The unique identifier for the chat room. This ID is used to uniquely distinguish each chat room
     * within the system, allowing users and the system to reference and access the correct chat room.
     */
    private int chatIDX;

    /**
     * The category of the chat room, which describes the general topic or the purpose of the chat room.
     * Categories help users quickly identify the type of conversation or content they can expect in the chat room,
     * such as 'tech', 'hobbies', 'support', etc.
     */
    private String category;

    /**
     * A brief summary of the chat room, providing a quick overview or description of what the chat room is about.
     * This can include information about the chat room's focus, recent topics of discussion, or any other
     * relevant information that would be useful for users deciding whether to join.
     */
    private String Summary;

    /**
     * The date and time of the last message sent in the chat room. This helps users see how active the chat room is
     * or when the last interaction took place, which can be important for chat rooms that are time-sensitive
     * or rely on frequent updates.
     */
    private Date lastDate;
}
