package kr.co.MyPick_server.controller.chat;

import kr.co.MyPick_server.DTO.chat.ChatReq;
import kr.co.MyPick_server.DTO.chat.ChatRes;
import kr.co.MyPick_server.Service.chat.ChatServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/openai")
public class ChatController {

    @Autowired
    private ChatServiceImpl openAIService;

    @GetMapping("/ask")
    public ChatRes askOpenAI(@ModelAttribute ChatReq chatReq) {
        return openAIService.getResponse(chatReq);
    }
}