package kr.co.MyPick_server.controller.chat;

import kr.co.MyPick_server.DTO.chat.CategoryChangeReq;
import kr.co.MyPick_server.DTO.chat.ChatListDTO;
import kr.co.MyPick_server.DTO.chat.ChatReq;
import kr.co.MyPick_server.DTO.chat.ChatRes;
import kr.co.MyPick_server.Service.JWT.JWTService;
import kr.co.MyPick_server.Service.chat.CategotyService;
import kr.co.MyPick_server.Service.chat.ChatService;
import kr.co.MyPick_server.Util.ResponsData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;
    @Autowired
    private CategotyService categotyService;

    @Autowired
    private JWTService jwtService;

    Logger logger = LoggerFactory.getLogger(ChatController.class);

    @GetMapping("/list")
    public ResponseEntity<?> list(@RequestParam String CT_AT) {
        logger.info("===================================================");
        logger.info("list");
        logger.info("CT_AT : {}", CT_AT);
        ResponsData data = new ResponsData();

        // JWT 토큰 검증 로직
        int IDX = jwtService.extractKey(CT_AT);
        if (IDX == -1) {
            data.setCode("401");
            data.setMessage("CT_AT does not exist.");
            return ResponseEntity.ok(data);
        }
        if (IDX == 0) {
            data.setCode("402");
            data.setMessage("Your time has expired.");
            return ResponseEntity.ok(data);
        }

        List<ChatListDTO> chatList = chatService.ChatListGet(IDX);
        data.setData(chatList);


        return ResponseEntity.ok(data);
    }

    @GetMapping("/question")
    public ResponseEntity<?> question(@ModelAttribute ChatReq chatReq) {
        logger.info("===================================================");
        logger.info("question");
        logger.info("chatReq : {}", chatReq.toString());
        ResponsData data = new ResponsData();

        // JWT 토큰 검증 로직
        int IDX = jwtService.extractKey(chatReq.getCT_AT());
        if (IDX == -1) {
            data.setCode("401");
            data.setMessage("CT_AT does not exist.");
            return ResponseEntity.ok(data);
        }
        if (IDX == 0) {
            data.setCode("402");
            data.setMessage("Your time has expired.");
            return ResponseEntity.ok(data);
        }

        if (chatReq.getChatIDX() == 0) {
            String prompt = chatReq.getPrompt().replaceFirst("^\\s+", ""); // 맨 앞 공백 제거
            int result = 0;
            if (prompt.startsWith("/")) {
                result = categotyService.ChatCreateRoom(IDX, prompt);

            }
            else {
                result = categotyService.ChatCreateRoom(IDX, "/음악");
            }
            if (result == 0) {
                data.setCode("403");
                data.setMessage("Category Change Failed.");
            }
        }

        //====================================================
        // 요청이 '/'로 시작하는지 확인

        else {
            ChatRes result = chatService.getResponse(IDX, chatReq.getPrompt());
            data.setData(result);
        }

        return ResponseEntity.ok(data);
    }

}
