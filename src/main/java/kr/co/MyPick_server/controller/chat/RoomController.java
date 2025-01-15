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

/**
 * RoomController is responsible for handling API requests related to managing chat rooms.
 * This includes retrieving a list of chat rooms and deleting an existing room.
 */
@RestController
@RequestMapping("/api/chat/room")
public class RoomController {

    @Autowired
    RoomService roomService;
    @Autowired
    private JWTService jwtService;

    Logger logger = LoggerFactory.getLogger(RoomController.class);

    /**
     * Retrieves a list of chat rooms for the authenticated user.
     *
     * @param CT_AT The JWT token passed as a request parameter for validation.
     * @return A ResponseEntity containing a list of ChatRoomListDTO objects or an error code/message.
     */
    @GetMapping("/roomList")
    public ResponseEntity<?> list(@RequestParam String CT_AT) {
        logger.info("===================================================");
        logger.info("list");
        logger.info("CT_AT : {}", CT_AT);

        // Create a uniform response object to send consistent responses
        ResponsData data = new ResponsData();

        // Extract the user identifier (IDX) from the provided JWT token
        int IDX = jwtService.extractKey(CT_AT);
        logger.info("Extracted IDX: {}", IDX);

        // Validate the extracted token state and respond accordingly
        if (IDX == -2) {
            // -2 indicates the user account is suspended
            data.setCode("509");
            data.setMessage("Your account has been suspended.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }
        if (IDX == -1) {
            // -1 indicates the token is missing or invalid
            data.setCode("503");
            data.setMessage("CT_AT does not exist.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }
        if (IDX == 0) {
            // 0 indicates the token has expired
            data.setCode("504");
            data.setMessage("Your time has expired.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }

        // If the token is valid, retrieve the list of chat rooms
        List<ChatRoomListDTO> chatList = roomService.RoomListGet(IDX);
        data.setData(chatList);

        // Return the list of chat rooms
        logger.info(data.toString());
        return ResponseEntity.ok(data);
    }

    /**
     * Deletes a specific chat room based on the provided chatIDX.
     *
     * @param chatMessageListReq The request object containing the chatIDX and the JWT token.
     * @return A ResponseEntity indicating success or an error code/message.
     */
    @GetMapping("/roomDelete")
    public ResponseEntity<?> roomDelete(@ModelAttribute ChatMessageListReq chatMessageListReq) {
        logger.info("===================================================");
        logger.info("roomDelete");
        logger.info("ChatMessageListReq : {}", chatMessageListReq);

        // Create a uniform response object to send consistent responses
        ResponsData data = new ResponsData();

        // Extract the user identifier (IDX) from the provided JWT token
        int IDX = jwtService.extractKey(chatMessageListReq.getCT_AT());

        // Validate the extracted token state and respond accordingly
        if (IDX == -2) {
            // -2 indicates the user account is suspended
            data.setCode("509");
            data.setMessage("Your account has been suspended.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }
        if (IDX == -1) {
            // -1 indicates the token is missing or invalid
            data.setCode("503");
            data.setMessage("CT_AT does not exist.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }
        if (IDX == 0) {
            // 0 indicates the token has expired
            data.setCode("504");
            data.setMessage("Your time has expired.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }

        // Attempt to delete the specified room
        int result = roomService.RoomDelete(IDX, chatMessageListReq.getChatIDX());
        if (result == 0) {
            // If room deletion fails, return an error code
            data.setCode("533");
            data.setMessage("Room Delete failed.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }

        // If successful, return a success message indicating which room was deleted
        data.setMessage("Room Number " + chatMessageListReq.getChatIDX() + " is deleted");
        logger.info(data.toString());
        return ResponseEntity.ok(data);
    }
}
