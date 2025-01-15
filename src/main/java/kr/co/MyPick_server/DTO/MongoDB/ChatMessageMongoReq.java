package kr.co.MyPick_server.DTO.MongoDB;

import kr.co.MyPick_server.DTO.chat.ChatMessage.ChatMessageListInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Data Transfer Object (DTO) for storing and retrieving chat message details from MongoDB.
 * This class is mapped to the "Chat_List" collection in MongoDB.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Chat_List") // Specify the MongoDB collection name
public class ChatMessageMongoReq {

    @Id
    private String id;            // Unique identifier in MongoDB documents
    private int userIdx;          // User index associated with the message
    private int chatIdx;          // Chat room index where the message belongs
    private LocalDateTime date;   // Date and time when the message was added
    private String question;      // The question or input from the user
    private String answer;        // The system-generated answer to the question
    private String summary;       // A summary or concise version of the conversation if applicable
    private List<ChatMessageListInfoDTO> list; // List of message details, possibly for extended chat information

}
