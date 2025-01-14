package kr.co.MyPick_server.DAO.Chat;

import kr.co.MyPick_server.DTO.MongoDB.ChatMessageMongoReq;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
public interface ChatMongoDAO extends MongoRepository<ChatMessageMongoReq, Integer> {
    /**
     * chatIdx와 userIdx로 모든 채팅 조회
     * @param userIdx 유저 IDX
     * @param chatIdx 채팅 방 IDX
     * @return 조건에 맞는 Chat 리스트
     */
//    List<ChatMongoInsertReq> findByChatIdxAndUserIdx(int chatIdx, int userIdx);


    /**
     * chatIdx와 userIdx로 마지막 채팅 조회
     * @param chatIdx 채팅 방 IDX
     * @param userIdx 유저 IDX
     * @param sort 정렬 기준 (날짜 역순)
     * @return 조건에 맞는 가장 마지막 Chat
     */
    List<ChatMessageMongoReq> findByChatIdxAndUserIdx(int chatIdx, int userIdx, Sort sort);
}