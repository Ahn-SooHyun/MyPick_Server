package kr.co.MyPick_server.controller.chat;

import kr.co.MyPick_server.DTO.chat.ChatMessageList.ChatMessageListReq;
import kr.co.MyPick_server.DTO.chat.ChatRoom.ChatRoomListDTO;
import kr.co.MyPick_server.Service.JWT.JWTService;
import kr.co.MyPick_server.Service.chat.RoomService;
import kr.co.MyPick_server.Util.ResponsData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat/room")
public class RoomController {

    @Autowired
    RoomService roomService;

    @Autowired
    private JWTService jwtService;

    Logger logger = LoggerFactory.getLogger(RoomController.class);

    @GetMapping("/roomList")
    public ResponseEntity<?> list(@RequestParam String CT_AT) {
        logger.info("===================================================");
        logger.info("list");
        logger.info("CT_AT : {}", CT_AT);
        ResponsData data = new ResponsData();

        // JWT 토큰 검증 로직
        int IDX = jwtService.extractKey(CT_AT);
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

        List<ChatRoomListDTO> chatList = roomService.RoomListGet(IDX);
        data.setData(chatList);


        return ResponseEntity.ok(data);
    }

    @GetMapping("/roomDelete")
    public ResponseEntity<?> roomDelete(@ModelAttribute ChatMessageListReq chatMessageListReq) {
        logger.info("===================================================");
        logger.info("roomDelete");
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

        int result = roomService.RoomDelete(IDX, chatMessageListReq.getChatIDX());
        if (result == 0) {
            data.setCode("533");
            data.setMessage("Room Delete failed.");
            return ResponseEntity.ok(data);
        }

        data.setMessage("Room Number " + chatMessageListReq.getChatIDX() + " is deleted");

        return ResponseEntity.ok(data);
    }
}
