package kr.co.MyPick_server.Service.loginRegister;

import kr.co.MyPick_server.DTO.loginReigster.LoginDTO;
import kr.co.MyPick_server.DTO.loginReigster.LoginReq;

public interface LoginServiceImpl {

    /**
     * Retrieves login details for a specific user based on the provided ID.
     *
     * @param IDX The unique identifier (ID) of the user.
     * @return A `LoginDTO` object containing the user's login-related information.
     */
    LoginDTO login(int IDX);

    /**
     * Checks if the provided token is valid for automatic login.
     *
     * @param token The token used to verify automatic login.
     * @return Returns 1 if the token is valid, otherwise returns 0.
     */
    int autoLoginCheck(String token);

    /**
     * Validates the user's login credentials such as username and password.
     *
     * @param loginReq An object containing the user's login request data, including
     *                 username and password.
     * @return Returns 1 if the login credentials are valid, otherwise returns 0.
     */
    int loginCheck(LoginReq loginReq);

}
