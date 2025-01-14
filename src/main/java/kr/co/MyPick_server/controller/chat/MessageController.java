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

@RestController
@RequestMapping("/api/chat/message")
public class MessageController {

    @Autowired
    private MessageService chatService;

    @Autowired
    private JWTService jwtService;

    Logger logger = LoggerFactory.getLogger(MessageController.class);

    @GetMapping("/messageList")
    public ResponseEntity<?> messageList(@ModelAttribute ChatMessageListReq chatMessageListReq) {
        logger.info("===================================================");
        logger.info("messageList");
        logger.info("ChatMessageListReq : {}", chatMessageListReq);
        ResponsData data = new ResponsData();

        // JWT 토큰 검증 로직
        int IDX = jwtService.extractKey(chatMessageListReq.getCT_AT());
        if (IDX == -2) {
            data.setCode("509"); // Unauthorized
            data.setMessage("Your account has been suspended.");
            return ResponseEntity.ok(data);
        }
        if (IDX == -1) {
            data.setCode("503");
            data.setMessage("CT_AT does not exist.");
            return ResponseEntity.ok(data);
        }
        if (IDX == 0) {
            data.setCode("504");
            data.setMessage("Your time has expired.");
            return ResponseEntity.ok(data);
        }

        ChatMessageListGetReq chatingListGetReq = new ChatMessageListGetReq();
        chatingListGetReq.setUserIDX(IDX);
        chatingListGetReq.setChatIDX(chatMessageListReq.getChatIDX());

        List<ChatMessageMongoReq> result = chatService.ChatListGet(chatingListGetReq);

        if (result == null) {
            data.setCode("531");
            data.setMessage("chatIDX does not match.");
            return ResponseEntity.ok(data);
        }

        data.setData(result);

        return ResponseEntity.ok(data);
    }

    @GetMapping("/message")
    public ResponseEntity<?> message(@ModelAttribute ChatMessageReq chatReq) {
        logger.info("===================================================");
        logger.info("message");
        logger.info("ChatMessageReq : {}", chatReq.toString());
        ResponsData data = new ResponsData();

        // JWT 토큰 검증 로직
        int IDX = jwtService.extractKey(chatReq.getCT_AT());
        if (IDX == -2) {
            data.setCode("509"); // Unauthorized
            data.setMessage("Your account has been suspended.");
            return ResponseEntity.ok(data);
        }
        if (IDX == -1) {
            data.setCode("503");
            data.setMessage("CT_AT does not exist.");
            return ResponseEntity.ok(data);
        }
        if (IDX == 0) {
            data.setCode("504");
            data.setMessage("Your time has expired.");
            return ResponseEntity.ok(data);
        }


        boolean SlashCheck = true;
        if (chatReq.getChatIDX() == 0) {
            String prompt = chatReq.getPrompt().replaceFirst("^\\s+", ""); // 맨 앞 공백 제거
            if (prompt.startsWith("/")) {
                chatReq.setChatIDX(chatService.ChatCreateRoom(IDX, prompt, false));
                if (chatReq.getChatIDX() != 0){
                    data.setMessage("Create Chat Room.");
                }
                SlashCheck = false;
            }
            else {
                chatReq.setChatIDX(chatService.ChatCreateRoom(IDX, "/음악", true));
            }

            if (chatReq.getChatIDX() == 0) {
                data.setCode("532");
                data.setMessage("Create Chat Room Failed.");
                return ResponseEntity.ok(data);
            }
        }

        ChatMessageDTO result;
        if (SlashCheck) {
            result = chatService.chatMessageSend(IDX, chatReq.getChatIDX(), chatReq.getPrompt());
            data.setData(result);
        }

        return ResponseEntity.ok(data);
    }

}
