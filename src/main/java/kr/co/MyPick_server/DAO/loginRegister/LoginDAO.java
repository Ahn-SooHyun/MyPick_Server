package kr.co.MyPick_server.DAO.loginRegister;

import kr.co.MyPick_server.DTO.loginReigster.LoginReq;
import kr.co.MyPick_server.DTO.loginReigster.LoginUpdateRes;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * LoginDAO is a Data Access Object (DAO) interface for managing user login operations.
 * This interface interacts with the database to perform tasks such as verifying user credentials,
 * managing login tokens, updating login-related information, and checking user permissions.
 */
@Mapper
public interface LoginDAO {

    /**
     * Checks the validity of a token for automatic login.
     * This method verifies whether the provided token is valid for a specific user session.
     *
     * @param token The token used for automatic login verification.
     * @return A map containing:
     *         - Verification status
     *         - User-related data if the token is valid
     */
    Map<String, Object> autoLoginCheck(String token);

    /**
     * Verifies the user's login credentials against stored data in the database.
     * This method checks if the provided username and password match the records.
     *
     * @param loginReq A DTO containing the user's login details, such as username and password.
     * @return A map containing:
     *         - Verification status
     *         - User-related data if the credentials are valid
     */
    Map<String, Object> loginCheck(LoginReq loginReq);

    /**
     * Updates the user's login-related information in the database.
     * Common use cases include updating the last login timestamp, resetting failed login attempts,
     * or recording login metadata.
     *
     * @param loginUpdateRes A DTO containing the updated login information to be stored in the database.
     */
    void loginUpdate(LoginUpdateRes loginUpdateRes);

    /**
     * Checks whether the user has administrative privileges.
     * This method queries the database to determine if the user associated with the given ID
     * has general (admin) permissions.
     *
     * @param IDX The unique identifier for the user whose administrative status is being checked.
     * @return A string indicating the user's administrative status:
     *         - "1" for administrators
     *         - "0" for regular users
     */
    String GeneralCheck(int IDX);
}
