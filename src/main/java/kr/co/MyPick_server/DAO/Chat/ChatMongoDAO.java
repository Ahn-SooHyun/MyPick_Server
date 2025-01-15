package kr.co.MyPick_server.DAO.Chat;

import kr.co.MyPick_server.DTO.MongoDB.ChatMessageMongoReq;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * ChatMongoDAO is a Data Access Object (DAO) interface for managing chat-related operations
 * in MongoDB. It extends MongoRepository, providing built-in methods for CRUD operations,
 * and custom query methods to fetch chat data based on specific criteria.
 */
public interface ChatMongoDAO extends MongoRepository<ChatMessageMongoReq, Integer> {

    /**
     * Retrieves the most recent chat messages from a specific chat room and user, sorted by a given criterion.
     * This method is useful for fetching the latest chat records for a user in a particular chat room.
     *
     * @param chatIdx The unique identifier of the chat room.
     * @param userIdx The unique identifier of the user.
     * @param sort    The sorting criteria, typically used to sort messages by date in descending order.
     * @return A list of chat messages (`ChatMessageMongoReq`) that match the specified chat room and user,
     *         sorted according to the provided criteria.
     */
    List<ChatMessageMongoReq> findByChatIdxAndUserIdx(int chatIdx, int userIdx, Sort sort);
}
