package kr.co.MyPick_server.DTO.chat.ChatMessage;

import lombok.Data;

/**
 * ChatMessageListInfoDTO is a Data Transfer Object (DTO) designed to encapsulate and convey information
 * about specific items, such as video games, that are discussed within chat messages. This class is utilized
 * to structure and present details about games or other media content in chat applications, facilitating
 * organized and context-rich discussions among users.
 */
@Data
public class ChatMessageListInfoDTO {

    /**
     * The title of the item, which could be a video game or any other form of creative content. It may include
     * additional identifiers such as the release year, which aids users in precisely identifying and discussing
     * specific versions or editions of the content within the chat environment.
     */
    private String title;

    /**
     * The name of the creator or the development studio responsible for creating the item. This information is
     * essential for attributing the work to its respective creators, fostering recognition and informed
     * discussions about the creative aspects of the item among chat participants.
     */
    private String creator;
}
