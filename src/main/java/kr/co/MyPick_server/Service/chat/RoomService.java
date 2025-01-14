package kr.co.MyPick_server.Service.chat;

import kr.co.MyPick_server.DAO.Chat.ChatDAO;
import kr.co.MyPick_server.DAO.Chat.ChatMongoDAO;
import kr.co.MyPick_server.DTO.MongoDB.ChatMessageMongoReq;
import kr.co.MyPick_server.DTO.chat.ChatMessage.ChatMessageDTO;
import kr.co.MyPick_server.DTO.chat.ChatMessageList.ChatMessageListGetReq;
import kr.co.MyPick_server.DTO.chat.ChatRoom.ChatRoomCreateReq;
import kr.co.MyPick_server.DTO.chat.ChatRoom.ChatRoomListDTO;
import kr.co.MyPick_server.Util.AIChatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RoomService implements RoomServiceImpl {

    @Autowired
    private ChatDAO chatDAO;

    Logger logger = LoggerFactory.getLogger(RoomService.class);

    @Override
    public List<ChatRoomListDTO> RoomListGet(int IDX) {
        List<ChatRoomListDTO> chatList = chatDAO.ChatRoomListGet(IDX);
        for (int i = 0; i < chatList.size(); i++) {
            if (chatList.get(i).getSummary() == null) {
                chatList.get(i).setSummary("New Chat");
            }
        }

        return chatList;
    }

    @Override
    public int RoomCreate(int IDX, String prompt, boolean chatCheck) {
        // "/" 뒤 실제 명령어 부분 추출
        // 모든 공백을 제거한 문자열 (슬래시 명령어 여부 판단용)
        String noSpacePrompt = prompt.replaceAll("\\s+", "");
        String command = noSpacePrompt.substring(1);
        // 예: " / 음 악 " -> 공백 제거 -> "/음악" -> command = "음악"

        // CategoryChangeReq 생성
        ChatRoomCreateReq chatRoomCreateReq = new ChatRoomCreateReq();
        chatRoomCreateReq.setIDX(IDX);      // JWT에서 추출한 사용자 인덱스
        chatRoomCreateReq.setCategory(command);

        int result = 0;

        switch (chatRoomCreateReq.getCategory()) {
            case "음악":
            case "Music":
            case "music":
                chatRoomCreateReq.setCategory("음악");
                break;
            case "영화":
            case "Movie":
            case "movie":
                chatRoomCreateReq.setCategory("영화");
                break;
            case "도서":
            case "Book":
            case "book":
                chatRoomCreateReq.setCategory("도서");
                break;
            case "게임":
            case "Game":
            case "game":
                chatRoomCreateReq.setCategory("게임");
                break;
            default:
                return result;
        }

        chatDAO.ChatRoomCreate(chatRoomCreateReq);
        result = chatDAO.ChatRoomLastIDXGet(IDX);

        return result;
    }

    @Override
    public int RoomDelete(int IDX, int roomIDX) {
        ChatMessageListGetReq chatMessageListGetReq = new ChatMessageListGetReq();
        chatMessageListGetReq.setUserIDX(IDX);
        chatMessageListGetReq.setChatIDX(roomIDX);

        return chatDAO.ChatRoomDelete(chatMessageListGetReq);
    }

}
