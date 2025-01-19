package kr.co.MyPick_server.controller.chat;

import kr.co.MyPick_server.DTO.MongoDB.ChatMessageMongoReq;
import kr.co.MyPick_server.DTO.chat.ChatMessage.ChatMessageDTO;
import kr.co.MyPick_server.DTO.chat.ChatMessage.ChatMessageReq;
import kr.co.MyPick_server.DTO.chat.ChatMessageList.ChatMessageListGetReq;
import kr.co.MyPick_server.DTO.chat.ChatMessageList.ChatMessageListReq;
import kr.co.MyPick_server.Service.JWT.JWTService;
import kr.co.MyPick_server.Service.chat.MessageService;
import kr.co.MyPick_server.Util.ResponsData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * MessageController is responsible for handling chat message APIs related to message creation, retrieval, and validation.
 * It provides endpoints to:
 * 1. Get message list for a specific chat room.
 * 2. Send a chat message (possibly creating a new chat room).
 */
@RestController
@RequestMapping("/api/chat/message")
public class MessageController {

    @Autowired
    private MessageService chatService;
    @Autowired
    private JWTService jwtService;

    Logger logger = LoggerFactory.getLogger(MessageController.class);

    /**
     * Endpoint to get the list of messages within a specific chat room.
     *
     * @param chatMessageListReq The request model containing the chat room identifier and JWT token.
     * @return A ResponseEntity containing the response data with message list or an error code.
     */
    @GetMapping("/messageList")
    public ResponseEntity<?> messageList(@ModelAttribute ChatMessageListReq chatMessageListReq) {
        logger.info("===================================================");
        logger.info("messageList");
        logger.info("ChatMessageListReq : {}", chatMessageListReq);

        // A custom response data object to unify response structure
        ResponsData data = new ResponsData();

        // Extract user IDX (identifier) from the provided JWT token
        int IDX = jwtService.extractKey(chatMessageListReq.getCT_AT());
        data.setIdentification(IDX);

        // Check various token validation cases
        if (IDX == -2) {
            // -2 means the account is suspended
            data.setCode("509");
            data.setMessage("Your account has been suspended.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }
        if (IDX == -1) {
            // -1 means the token does not exist
            data.setCode("503");
            data.setMessage("CT_AT does not exist.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }
        if (IDX == 0) {
            // 0 means the token has expired
            data.setCode("504");
            data.setMessage("Your time has expired.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }

        // Prepare a request object to fetch chat messages
        ChatMessageListGetReq chatingListGetReq = new ChatMessageListGetReq();
        chatingListGetReq.setUserIDX(IDX);
        chatingListGetReq.setChatIDX(chatMessageListReq.getChatIDX());

        // Retrieve the list of messages from MongoDB using the chatService
        List<ChatMessageMongoReq> result = chatService.ChatListGet(chatingListGetReq);

        // If no result is found, return an error code
        if (result == null) {
            data.setCode("531");
            data.setMessage("There is no chat history.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }

        // Otherwise, set the result as data and return a successful response
        data.setData(result);

        logger.info(data.toString());
        return ResponseEntity.ok(data);
    }

    /**
     * Endpoint to send a message to a chat room. If a chat room does not exist (chatIDX == 0),
     * it may create a new room based on the prompt command.
     *
     * @param chatReq The request model containing chat identifier, prompt, and JWT token.
     * @return A ResponseEntity containing the response data with the sent message or an error code.
     */
    @GetMapping("/message")
    public ResponseEntity<?> message(@ModelAttribute ChatMessageReq chatReq) {
        logger.info("===================================================");
        logger.info("message");
        logger.info("ChatMessageReq : {}", chatReq.toString());

        // A custom response data object to unify response structure
        ResponsData data = new ResponsData();

        // Extract user IDX (identifier) from the provided JWT token
        int IDX = jwtService.extractKey(chatReq.getCT_AT());
        data.setIdentification(IDX);

        // Check various token validation cases
        if (IDX == -2) {
            // -2 means the account is suspended
            data.setCode("509");
            data.setMessage("Your account has been suspended.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }
        if (IDX == -1) {
            // -1 means the token does not exist
            data.setCode("503");
            data.setMessage("CT_AT does not exist.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }
        if (IDX == 0) {
            // 0 means the token has expired
            data.setCode("504");
            data.setMessage("Your time has expired.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }

        // This flag is used to check if the user input starts with a slash ("/").
        // If it does, a new room is created with the slash command as the room context.
        boolean SlashCheck = true;

        // If chatIDX is zero, we either create a new chat room based on slash command or a default type ("/음악")
        if (chatReq.getChatIDX() == 0) {
            // Remove any leading whitespace from the prompt
            String prompt = chatReq.getPrompt().replaceFirst("^\\s+", "");

            // If the prompt starts with "/", treat it as a command to create a room
            if (prompt.startsWith("/")) {
                // Attempt to create a new chat room with the provided slash command
                chatReq.setChatIDX(chatService.ChatCreateRoom(IDX, prompt, false));
                if (chatReq.getChatIDX() != 0){
                    data.setMessage("Create Chat Room.");
                }
                SlashCheck = false;
            } else {
                // If it doesn't start with "/", create a default chat room of type "/음악"
                chatReq.setChatIDX(chatService.ChatCreateRoom(IDX, "/음악", true));
            }

            // If no chat room could be created, return an error
            if (chatReq.getChatIDX() == 0) {
                data.setCode("532");
                data.setMessage("Create Chat Room Failed.");
                logger.info(data.toString());
                return ResponseEntity.ok(data);
            }
        }

        ChatMessageListGetReq chatMessageListGetReq = new ChatMessageListGetReq();
        chatMessageListGetReq.setUserIDX(IDX);
        chatMessageListGetReq.setChatIDX(chatReq.getChatIDX());
        int roomCheck = chatService.ChatRoomCheck(chatMessageListGetReq);
        logger.info(String.valueOf(roomCheck));
        if (roomCheck == 0) {
            data.setCode("539");
            data.setMessage("Wrong Room Number");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }

        // If we didn’t create a chat room using slash command, proceed to send the message
        ChatMessageDTO result;
        if (SlashCheck) {
            // Call the service to send the message to an existing chat room
            result = chatService.chatMessageSend(IDX, chatReq.getChatIDX(), chatReq.getPrompt());
            data.setData(result);
        }

        // Return the response data with the sent message
        logger.info(data.toString());
        return ResponseEntity.ok(data);
    }

}
