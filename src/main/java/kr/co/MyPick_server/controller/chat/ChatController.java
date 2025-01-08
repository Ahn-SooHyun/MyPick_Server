package kr.co.MyPick_server.controller.chat;

import kr.co.MyPick_server.DTO.chat.ChatReq;
import kr.co.MyPick_server.Service.JWT.JWTService;
import kr.co.MyPick_server.Service.chat.ChatService;
import kr.co.MyPick_server.Util.ResponsData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;
    @Autowired
    private JWTService jwtService;

    Logger logger = LoggerFactory.getLogger(ChatController.class);

    @GetMapping("/question")
    public ResponseEntity<?> question(@ModelAttribute ChatReq chatReq) {
        ResponsData data = new ResponsData();

        int result = jwtService.extractKey(chatReq.getCT_AT());
        if (result == -1) {
            data.setCode("401");
            data.setMessage("CT_AT does not exist.");
            return ResponseEntity.ok(data);
        }
        if (result == 0) {
            data.setCode("402");
            data.setMessage("Your time has expired.");
            return ResponseEntity.ok(data);
        }



        return ResponseEntity.ok(data);
    }

}