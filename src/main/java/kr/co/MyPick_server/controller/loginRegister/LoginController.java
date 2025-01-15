package kr.co.MyPick_server.controller.loginRegister;

import jakarta.validation.Valid;
import kr.co.MyPick_server.DTO.loginReigster.AutoLoginReq;
import kr.co.MyPick_server.DTO.loginReigster.LoginReq;
import kr.co.MyPick_server.Service.loginRegister.LoginService;
import kr.co.MyPick_server.Util.ResponsData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The LoginController class handles user login and auto-login requests.
 * It provides endpoints for validating login credentials and managing auto-login sessions.
 */
@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    Logger logger = LoggerFactory.getLogger(LoginController.class);

    /**
     * Endpoint to verify an auto-login token and retrieve user information.
     *
     * Flow:
     * - Validates the auto-login token provided by the user.
     * - Returns a status code and message based on the validity of the token:
     *   - -2: Account is suspended (code "509")
     *   - -1: Token is invalid or does not exist (code "501")
     *   - 0: Token has expired (code "502")
     *   - > 0: Valid user ID; user data is returned
     *
     * @param autoLoginReq The request body containing the auto-login token.
     * @return A ResponseEntity containing:
     *         - ResponsData with an error code and message if the token is invalid.
     *         - ResponsData with user data if the token is valid.
     */
    @PostMapping("/autoLogin")
    public ResponseEntity<?> autoLogin(@RequestBody AutoLoginReq autoLoginReq) {
        logger.info("===================================================");
        logger.info("autoLogin");
        logger.info("autoLoginReq: {}", autoLoginReq);

        ResponsData data = new ResponsData();

        // Validate the auto-login token and determine user state
        int IDX = loginService.autoLoginCheck(autoLoginReq.getToken());
        if (IDX == -2) {
            // User account is suspended
            data.setCode("509");
            data.setMessage("Your account has been suspended.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }
        if (IDX == -1) {
            // Token does not exist or is invalid
            data.setCode("501");
            data.setMessage("Auto-login does not exist.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }
        if (IDX == 0) {
            // Token has expired
            data.setCode("502");
            data.setMessage("Your time has expired.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }

        // If IDX > 0, the token is valid; retrieve user data
        data.setData(loginService.login(IDX));
        logger.info(data.toString());
        return ResponseEntity.ok(data);
    }

    /**
     * Endpoint to handle user login requests by validating the provided credentials.
     *
     * Flow:
     * - Validates user credentials (ID and password) provided in the request.
     * - Returns a status code and message based on the validity of the credentials:
     *   - -2: Account is suspended (code "509")
     *   - -1: Invalid credentials or user not found (code "500")
     *   -  0: Login session expired (code "502")
     *   - > 0: Valid user ID; user data is returned
     *
     * @param loginReq The request body containing user credentials (ID and password).
     * @return A ResponseEntity containing:
     *         - ResponsData with an error code and message if the login fails.
     *         - ResponsData with user data if the login is successful.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginReq loginReq) {
        logger.info("===================================================");
        logger.info("login");
        logger.info("loginReq: {}", loginReq);

        ResponsData data = new ResponsData();

        // Validate the login credentials
        int IDX = loginService.loginCheck(loginReq);
        if (IDX == -2) {
            // User account is suspended
            data.setCode("509");
            data.setMessage("Your account has been suspended.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }
        if (IDX == -1) {
            // Invalid credentials or user not found
            data.setCode("500");
            data.setMessage("Login does not exist.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }
        if (IDX == 0) {
            // Session expired
            data.setCode("502");
            data.setMessage("Your time has expired.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }

        // If IDX > 0, credentials are valid; retrieve user data
        data.setData(loginService.login(IDX));
        logger.info(data.toString());
        return ResponseEntity.ok(data);
    }
}
