package kr.co.MyPick_server.controller.chat;

import kr.co.MyPick_server.DTO.chat.ChatReq;
import kr.co.MyPick_server.DTO.chat.ChatRes;
import kr.co.MyPick_server.Service.JWT.JWTService;
import kr.co.MyPick_server.Service.chat.ChatServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private ChatServiceImpl openAIService;
    @Autowired
    private JWTService jwtService;

    Logger logger = LoggerFactory.getLogger(ChatController.class);

    @GetMapping("/question")
    public String question(@ModelAttribute ChatReq chatReq) {
        int result = jwtService.extractKey(chatReq.getJWT());
//        return openAIService.getResponse(chatReq);
        return "";
    }

}