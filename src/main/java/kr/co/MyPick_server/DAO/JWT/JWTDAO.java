package kr.co.MyPick_server.DAO.JWT;

import kr.co.MyPick_server.DTO.JWT.JWTReq;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * JWTDAO is a Data Access Object (DAO) interface for managing operations related to JSON Web Tokens (JWT).
 * This interface defines methods to retrieve, validate, and update JWT-related data in the database.
 */
@Mapper
public interface JWTDAO {

    /**
     * Retrieves JWT-related data for a specific user or session based on the provided identifier.
     * This method is used to fetch stored JWT information such as tokens, user metadata, or session details.
     *
     * @param IDX The unique identifier (ID) of the user or session.
     * @return A `JWTReq` object containing JWT-related data, including:
     *         - Token details
     *         - Associated user information
     *         - Other session metadata
     */
    JWTReq JWTdata(int IDX);

    /**
     * Validates the provided JWT token key by comparing it with stored data in the database.
     * This method ensures that the token is still valid and associated with the correct user or session.
     *
     * @param extractedKey The JWT token key extracted from the user's request.
     * @return A map containing the results of the JWT validation, including:
     *         - Validation status (e.g., valid, expired, or invalid)
     *         - Any additional information related to the token or session
     */
    Map<String, Object> JWTCheck(String extractedKey);

    /**
     * Updates the expiration or issued date of a JWT in the database for a specific user or session.
     * This method is typically used to refresh the token's validity period without requiring re-authentication.
     *
     * @param IDX The unique identifier (ID) of the user or session whose JWT data needs to be updated.
     */
    void JWTDateUpdate(Integer IDX);
}
