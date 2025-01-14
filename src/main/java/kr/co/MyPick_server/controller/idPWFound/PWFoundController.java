package kr.co.MyPick_server.controller.idPWFound;

import jakarta.validation.Valid;
import kr.co.MyPick_server.DTO.idPWFound.PWChangeReq;
import kr.co.MyPick_server.DTO.idPWFound.PWFoundReq;
import kr.co.MyPick_server.DTO.idPWFound.PWFoundCheckReq;
import kr.co.MyPick_server.Service.idPWFound.PWFoundServer;
import kr.co.MyPick_server.Util.ResponsData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The PWFoundController class handles API requests related to password recovery.
 * It provides endpoints for finding a user's account, verifying a recovery code,
 * and changing the user's password.
 */
@RestController
@RequestMapping("/api/Found")
public class PWFoundController {

    @Autowired
    PWFoundServer pwFoundServer;

    Logger logger = LoggerFactory.getLogger(PWFoundController.class);

    /**
     * Handles password recovery requests by validating user information.
     *
     * @param pwFoundReq The request containing user information (e.g., ID, email).
     * @return A ResponseEntity containing a ResponsData object indicating the result of the operation.
     *         If the user is not found, an error code and message are returned.
     */
    @PostMapping("/pwFound")
    public ResponseEntity<?> pwFound(@RequestBody @Valid PWFoundReq pwFoundReq) {
        logger.info("===================================================");
        logger.info("pwFound");
        logger.info("PWFoundReq : {}", pwFoundReq);
        ResponsData data = new ResponsData();

        int result = pwFoundServer.pwFound(pwFoundReq);

        if (result == 0) {
            data.setCode("521"); // Error code for user not found
            data.setMessage("User not found");
            return ResponseEntity.ok(data);
        }

        return ResponseEntity.ok(data);
    }

    /**
     * Handles verification of the password recovery code provided by the user.
     *
     * @param pwFoundCheckReq The request containing the recovery code and associated user information.
     * @return A ResponseEntity containing a ResponsData object with the verification result.
     */
    @PostMapping("/pwFoundCheck")
    public ResponseEntity<?> pwFoundCheck(@RequestBody @Valid PWFoundCheckReq pwFoundCheckReq) {
        logger.info("===================================================");
        logger.info("pwFoundCheck");
        logger.info("PWFoundCheckReq : {}", pwFoundCheckReq);
        ResponsData data = new ResponsData();

        String result = pwFoundServer.pwFoundCheckReq(pwFoundCheckReq);
        if (result == null) {
            data.setCode("522");
            data.setMessage("User not found");
        }

        data.setData(result);

        return ResponseEntity.ok(data);
    }

    /**
     * Handles password change requests by validating and updating the user's password.
     *
     * @param pwChangeReq The request containing the user's ID, new password, and verification details.
     * @return A ResponseEntity containing a ResponsData object indicating the result of the operation.
     *         If the password change fails, an error code and message are returned.
     */
    @PostMapping("/pwChange")
    public ResponseEntity<?> pwChange(@RequestBody @Valid PWChangeReq pwChangeReq) {
        logger.info("===================================================");
        logger.info("pwChange");
        logger.info("PWChangeReq : {}", pwChangeReq);
        ResponsData data = new ResponsData();

        int result = pwFoundServer.pwChange(pwChangeReq);

        if (result == 0) {
            data.setCode("523"); // Error code for password change failure
            data.setMessage("Password not changed");
        }

        return ResponseEntity.ok(data);
    }
}
