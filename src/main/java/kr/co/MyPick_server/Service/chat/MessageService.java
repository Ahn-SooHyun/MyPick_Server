package kr.co.MyPick_server.Service.chat;

import kr.co.MyPick_server.DAO.Chat.ChatDAO;
import kr.co.MyPick_server.DAO.Chat.ChatMongoDAO;
import kr.co.MyPick_server.DTO.MongoDB.ChatMessageMongoReq;
import kr.co.MyPick_server.DTO.chat.ChatMessage.ChatMessageDTO;
import kr.co.MyPick_server.DTO.chat.ChatMessageList.ChatMessageListGetReq;
import kr.co.MyPick_server.DTO.chat.ChatRoom.ChatRoomListDTO;
import kr.co.MyPick_server.DTO.chat.ChatRoom.ChatRoomCreateReq;
import kr.co.MyPick_server.Util.AIChatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService implements MessageServiceImpl {

    @Autowired
    private ChatDAO chatDAO;
    @Autowired
    private ChatMongoDAO chatMongoDAO;

    @Autowired
    private AIChatUtil aiChatUtil;

    Logger logger = LoggerFactory.getLogger(MessageService.class);

    @Override
    public List<ChatRoomListDTO> ChatListGet(int IDX) {
        List<ChatRoomListDTO> chatList = chatDAO.ChatRoomListGet(IDX);
        for (int i = 0; i < chatList.size(); i++) {
            if (chatList.get(i).getSummary() == null) {
                chatList.get(i).setSummary("New Chat");
            }
        }

        return chatList;
    }

    @Override
    public List<ChatMessageMongoReq> ChatListGet(ChatMessageListGetReq chatMessageListGetReq ) {

        Integer chatRoomIDX = chatDAO.ChatRoomIDXGet(chatMessageListGetReq);
        // `chatRoomIDX`가 null인지 확인
        if (chatRoomIDX == null) {
            logger.warn("ChatRoomIDX is null for request: {}", chatMessageListGetReq);
            return null;
        }
        // `chatRoomIDX`와 요청의 `chatIDX`가 일치하는지 확인
        if (!chatRoomIDX.equals(chatMessageListGetReq.getChatIDX())) {
            logger.warn("Mismatch between ChatRoomIDX: {} and requested ChatIDX: {}",
                    chatRoomIDX, chatMessageListGetReq.getChatIDX());
            return null;
        }

        logger.info(chatMessageListGetReq.toString());

        // MongoDB에서 데이터 가져오기
        List<ChatMessageMongoReq> chats;
        try {
            chats = chatMongoDAO.findByChatIdxAndUserIdx(
                    chatMessageListGetReq.getChatIDX(),
                    chatMessageListGetReq.getUserIDX(),
                    Sort.by(Sort.Direction.DESC, "date") // 날짜 기준 내림차순 정렬
            );

            if (chats == null || chats.isEmpty()) {
                logger.warn("No chats found for ChatIDX: {} and UserIDX: {}",
                        chatMessageListGetReq.getChatIDX(), chatMessageListGetReq.getUserIDX());
                return null;
            }

            logger.info("Chats retrieved successfully: {}", chats.size());
        } catch (Exception e) {
            logger.error("Error while fetching chats: {}", e.getMessage(), e);
            return null;
        }

        return chats;
    }

    @Override
    public int ChatCreateRoom(int IDX, String prompt, boolean chatCheck) {
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
    public ChatMessageDTO chatMessageSend(int IDX, int roomIDX, String prompt) {
        String Category = chatDAO.ChatRoomCategoryGet(roomIDX);

        List<ChatMessageMongoReq> chats = chatMongoDAO.findByChatIdxAndUserIdx(
                roomIDX,
                IDX,
                Sort.by(Sort.Direction.DESC, "date") // 날짜 기준 내림차순 정렬
        );

        // system 메시지 (category를 문구에 반영)
        String systemMessage = String.format("""
                당신은 "%s"을(를) 추천해주고 리스트를 만들어주는 "%s 추천가"입니다.
                아래의 규칙을 따라야 합니다:

                1. [답변]: [질문]에 대한 답변을 최대 800글자 이내로 작성.
                2. [요약]: [답변]을 최대 30글자로 요약.
                3. [리스트]: [답변]의 리스트를 JSON 형태로 정리.

                출력 예시(반드시 JSON 형식으로 출력):

                {
                    
                    "answer" : "[답변]은 여기",
                    "summary" : "[요약]은 여기"
                    "list" : "[리스트]은 여기"
                }

                리스트 상세 예시(배열 등 자유롭게 편성 가능):
                [
                    {
                        "title": "제목 (발매 연도)",
                        "creator": "제작사"
                    },
                    ...
                ]

                다음 형식을 꼭 지키세요.
                """, Category, Category);

        ChatMessageMongoReq chat;
        String userMessage;

        if (chats.isEmpty()) {
            userMessage = String.format("""
                [질문]
                %s

                """, prompt);

        }
        else {
            chat = chats.get(0);
            // user 메시지 (question, lastQuestion, lastAnswer를 문자열에 삽입)
            userMessage = String.format("""
                [질문]
                %s

                [지난 질문]
                %s

                [지난 답변]
                %s
                """, prompt, chat.getQuestion(), chat.getAnswer());
        }

        logger.info("systemMessage result: {}", systemMessage);
        logger.info("userMessage result: {}", userMessage);

        ChatMessageDTO result;
        try {
            result = aiChatUtil.sendChat(systemMessage, userMessage);
            if (result == null) {
                throw new IllegalStateException("AIChatUtil returned null.");
            }
            logger.info("AIChatUtil result: {}", result.toString());

        } catch (Exception e) {
            logger.error("Error while calling AIChatUtil: {}", e.getMessage(), e);
            // 결과가 null인 경우 null 반환 또는 에러 응답 처리
            return null;
        }

        // MongoDB에 저장할 데이터를 생성
        ChatMessageMongoReq chatMongoInsertReq = ChatMessageMongoReq.builder()
                .userIdx(IDX)                     // 사용자 ID
                .chatIdx(roomIDX)                 // 채팅방 ID
                .date(LocalDateTime.now())        // 현재 시간
                .question(prompt)                 // 질문 내용
                .answer(result.getAnswer())       // AI가 생성한 답변
                .summary(result.getSummary())
                .list(result.getList())           // AI가 생성한 리스트
                .build();

        // MongoDB에 저장
        chatMongoDAO.save(chatMongoInsertReq);
        logger.info("Chat saved to MongoDB: {}", chatMongoInsertReq);

        chatDAO.ChatRoomUpdate(chatMongoInsertReq);

        return result;

    }

    @Override
    public int ChatRoomCheck(ChatMessageListGetReq chatMessageListGetReq) {
        Integer result = chatDAO.ChatRoomCheck(chatMessageListGetReq);
        if (result == null) {
            return 0;
        }

        return result;
    }

}
