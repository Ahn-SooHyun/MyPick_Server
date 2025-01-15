package kr.co.MyPick_server.DTO.chat.ChatRoom;

import lombok.Data;

/**
 * ChatListGetReq is a Data Transfer Object (DTO) used to request details of specific chat rooms.
 * This DTO is typically used to retrieve the list of messages or room details for a given chat room,
 * based on user and chat room identifiers.
 */
@Data
public class ChatListGetReq {
    /**
     * IDX represents the user identifier. This identifier is used to ensure that the request
     * is associated with the correct user, allowing the system to retrieve chat rooms
     * specific to this user. It is crucial for maintaining user-specific data integrity
     * and ensuring that users can only access chat information pertinent to them.
     */
    private int IDX;

    /**
     * chatIDX is the identifier for the specific chat room. This identifier is used to fetch
     * all relevant details or messages from this particular chat room. A value of 0 or greater
     * indicates valid room identifiers, with specific non-zero values pointing to individual
     * rooms and possibly zero representing a request for default or initial rooms, depending
     * on the system's design.
     */
    private int chatIDX; // Must be a non-negative value (0 or greater)
}
