package kr.co.MyPick_server.DAO.idPWFound;

import kr.co.MyPick_server.DTO.JWT.JWTReq;
import kr.co.MyPick_server.DTO.idPWFound.IDFoundReq;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IDFoundDAO {
    /**
     * Searches the database for the user's ID based on the input request data.
     *
     * @param idFoundReq An object containing the request data required to find the user's ID,
     *                   such as name, email, or other identifying information.
     * @return The user's ID as a String if found.
     *         Returns null if no matching user is found.
     */
    String IDFound(IDFoundReq idFoundReq);
}
