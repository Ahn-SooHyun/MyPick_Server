package kr.co.MyPick_server.DAO.admin;

import kr.co.MyPick_server.DTO.MongoDB.AdminMessageMongoReq;
import kr.co.MyPick_server.DTO.MongoDB.ChatMessageMongoReq;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * AdminMongoDAO is a Data Access Object (DAO) interface for managing administrative and chat-related data
 * stored in MongoDB. It extends `MongoRepository` to provide basic CRUD operations and custom query methods
 * for fetching specific data.
 */
public interface AdminMongoDAO extends MongoRepository<ChatMessageMongoReq, Integer> {

    /**
     * Retrieves chat messages associated with a specific user, sorted according to the specified criteria.
     * This method is particularly useful for fetching the latest or most relevant chat records for administrative
     * purposes, such as reviewing user activity.
     *
     * @param userIdx The unique identifier of the user whose messages are to be retrieved.
     * @param sort    The sorting criteria, typically by date in descending order, to get the latest messages first.
     * @return A list of `AdminMessageMongoReq` objects representing the chat messages associated with the specified user.
     */
    List<AdminMessageMongoReq> findByUserIdx(int userIdx, Sort sort);
}
