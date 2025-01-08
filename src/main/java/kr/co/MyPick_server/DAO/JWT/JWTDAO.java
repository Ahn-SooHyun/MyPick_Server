package kr.co.MyPick_server.DAO.JWT;

import kr.co.MyPick_server.DTO.JWT.JWTReq;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface JWTDAO {

    /**
     * Retrieves JWT-related data for a specific user or session based on the given ID.
     *
     * @param IDX The unique identifier (ID) of the user or session.
     * @return A `JWTReq` object containing JWT-related data (e.g., token, user information).
     */
    JWTReq JWTdata(int IDX);

    /**
     * Checks the validity of a provided JWT by comparing it with stored data.
     *
     * @param extractedKey The JWT token key extracted from the user's request.
     * @return A map containing details about the JWT validation, such as status and related data.
     */
    Map<String, Object> JWTCheck(String extractedKey);

    /**
     * Updates the expiration or issued date of a JWT in the database for a specific user or session.
     *
     * @param IDX The unique identifier (ID) of the user or session whose JWT date needs to be updated.
     */
    void JWTDateUpdate(Integer IDX);
}
