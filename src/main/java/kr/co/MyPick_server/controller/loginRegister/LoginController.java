package kr.co.MyPick_server.controller.loginRegister;

import jakarta.validation.Valid;
import kr.co.MyPick_server.DTO.loginReigster.AutoLoginReq;
import kr.co.MyPick_server.DTO.loginReigster.LoginReq;
import kr.co.MyPick_server.Service.loginRegister.LoginService;
import kr.co.MyPick_server.Util.ResponsData;
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

    /**
     * Handles auto-login requests by validating the provided token.
     *
     * @param autoLoginReq The auto-login request containing the token to validate.
     * @return A ResponseEntity containing a ResponsData object with the login result or an error message.
     */
    @PostMapping("/autoLogin")
    public ResponseEntity<?> autoLogin(@RequestBody AutoLoginReq autoLoginReq) {
        ResponsData data = new ResponsData();

        // Check the validity of the auto-login token
        int IDX = loginService.autoLoginCheck(autoLoginReq.getTocken());
        if (IDX == -1) {
            data.setCode("401"); // Unauthorized
            data.setMessage("Auto-login does not exist.");
            return ResponseEntity.ok(data);
        }
        if (IDX == 0) {
            data.setCode("402"); // Token expired
            data.setMessage("Your time has expired.");
            return ResponseEntity.ok(data);
        }

        // Retrieve user information and set it in the response
        data.setData(loginService.login(IDX));

        return ResponseEntity.ok(data);
    }

    /**
     * Handles user login requests by validating the provided credentials.
     *
     * @param loginReq The login request containing the user's credentials (ID, password).
     * @return A ResponseEntity containing a ResponsData object with the login result or an error message.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginReq loginReq) {
        ResponsData data = new ResponsData();

        // Check the validity of the login credentials
        int IDX = loginService.loginCheck(loginReq);
        if (IDX == -1) {
            data.setCode("401"); // Unauthorized
            data.setMessage("Login does not exist.");
            return ResponseEntity.ok(data);
        }
        if (IDX == 0) {
            data.setCode("402"); // Login session expired
            data.setMessage("Your time has expired.");
            return ResponseEntity.ok(data);
        }

        // Retrieve user information and set it in the response
        data.setData(loginService.login(IDX));

        return ResponseEntity.ok(data);
    }

}
