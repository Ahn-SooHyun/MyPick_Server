package kr.co.MyPick_server.DAO.idPWFound;

import kr.co.MyPick_server.DTO.JWT.JWTReq;
import kr.co.MyPick_server.DTO.idPWFound.IDFoundReq;
import org.apache.ibatis.annotations.Mapper;

/**
 * IDFoundDAO is a Data Access Object (DAO) interface for managing operations related to retrieving user IDs.
 * It defines methods for searching and retrieving user information based on provided criteria.
 */
@Mapper
public interface IDFoundDAO {

    /**
     * Searches the database for a user's ID based on the provided request data.
     * This method is used during the ID recovery process to help users retrieve their account ID.
     *
     * @param idFoundReq A DTO containing the necessary information to identify the user, such as:
     *                   - Name
     *                   - Birthdate
     *                   - Additional identifying details (if applicable)
     * @return The user's ID as a String if a matching record is found in the database.
     *         If no matching record is found, the method returns null.
     */
    String IDFound(IDFoundReq idFoundReq);
}
