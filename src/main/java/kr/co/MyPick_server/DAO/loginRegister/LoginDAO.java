package kr.co.MyPick_server.DAO.loginRegister;

import kr.co.MyPick_server.DTO.loginReigster.LoginReq;
import kr.co.MyPick_server.DTO.loginReigster.LoginUpdateRes;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface LoginDAO {

    /**
     * Checks if the provided token is valid for automatic login.
     *
     * @param token The token used for automatic login verification.
     * @return A map containing the verification status and related user data if the token is valid.
     */
    Map<String, Object> autoLoginCheck(String token);

    /**
     * Verifies the user's login credentials (e.g., username and password).
     *
     * @param loginReq An object containing the user's login details such as username and password.
     * @return A map containing the login verification status and user-related data.
     */
    Map<String, Object> loginCheck(LoginReq loginReq);

    /**
     * Updates the user's login-related information in the database.
     * This may include updating the last login timestamp or resetting login attempts.
     *
     * @param loginUpdateRes An object containing the data to be updated for the user's login information.
     */
    void loginUpdate(LoginUpdateRes loginUpdateRes);
}
