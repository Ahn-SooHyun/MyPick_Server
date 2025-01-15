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
import org.springframework.web.bind.annotation.*;

/**
 * The PWFoundController class handles API requests related to password recovery.
 * It provides endpoints for:
 * 1. Validating user information to trigger a password recovery process.
 * 2. Verifying a recovery code sent to the user.
 * 3. Updating the user's password upon successful verification.
 */
@RestController
@RequestMapping("/api/Found")
public class PWFoundController {

    @Autowired
    PWFoundServer pwFoundServer;

    Logger logger = LoggerFactory.getLogger(PWFoundController.class);

    /**
     * Endpoint to initiate the password recovery process by verifying user information (e.g., user ID, email).
     *
     * @param pwFoundReq A request body containing user identification details (ID, email, etc.) for password recovery.
     * @return A ResponseEntity containing a ResponsData object:
     *         - If the user is successfully identified, the operation proceeds (result code not explicitly set here).
     *         - If the user does not exist, returns code "521" and a "User not found" message.
     */
    @PostMapping("/pwFound")
    public ResponseEntity<?> pwFound(@RequestBody @Valid PWFoundReq pwFoundReq) {
        logger.info("===================================================");
        logger.info("pwFound");
        logger.info("PWFoundReq : {}", pwFoundReq);

        // Create a standardized response object
        ResponsData data = new ResponsData();

        // Attempt to validate user information and trigger a password recovery process
        int result = pwFoundServer.pwFound(pwFoundReq);

        // If the user is not found in the system, return an error code
        if (result == 0) {
            data.setCode("521");  // Custom error code indicating "User not found"
            data.setMessage("User not found");
            return ResponseEntity.ok(data);
        }

        // If the user is found, proceed without setting an error code (business logic might send an email, etc.)
        return ResponseEntity.ok(data);
    }

    /**
     * Endpoint to verify the recovery code that was provided to the user (e.g., via email).
     *
     * @param pwFoundCheckReq A request body containing the recovery code and user details for verification.
     * @return A ResponseEntity containing a ResponsData object:
     *         - If verification fails (result is null), returns code "522" and "User not found".
     *         - If verification succeeds, the verified code or some relevant data is set in ResponsData.
     */
    @PostMapping("/pwFoundCheck")
    public ResponseEntity<?> pwFoundCheck(@RequestBody @Valid PWFoundCheckReq pwFoundCheckReq) {
        logger.info("===================================================");
        logger.info("pwFoundCheck");
        logger.info("PWFoundCheckReq : {}", pwFoundCheckReq);

        // Create a standardized response object
        ResponsData data = new ResponsData();

        // Attempt to verify the provided recovery code
        String result = pwFoundServer.pwFoundCheckReq(pwFoundCheckReq);

        // If result is null, it indicates the code is invalid or the user does not exist
        if (result == null) {
            data.setCode("522");  // Error code indicating "User not found" or invalid code
            data.setMessage("User not found");
        }

        // Set the verification result (could be a token, success message, or some other data)
        data.setData(result);
        return ResponseEntity.ok(data);
    }

    /**
     * Endpoint to finalize the password recovery process by updating the user's password.
     *
     * @param pwChangeReq A request body containing the user's ID, new password, and recovery verification details.
     * @return A ResponseEntity containing a ResponsData object:
     *         - If the password change fails, returns code "523" and a "Password not changed" message.
     *         - Otherwise, responds successfully without an error code.
     */
    @PostMapping("/pwChange")
    public ResponseEntity<?> pwChange(@RequestBody @Valid PWChangeReq pwChangeReq) {
        logger.info("===================================================");
        logger.info("pwChange");
        logger.info("PWChangeReq : {}", pwChangeReq);

        // Create a standardized response object
        ResponsData data = new ResponsData();

        // Attempt to update the user's password in the system
        int result = pwFoundServer.pwChange(pwChangeReq);

        // If the result is 0, it indicates the password update did not complete successfully
        if (result == 0) {
            data.setCode("523");  // Custom error code indicating "Password not changed"
            data.setMessage("Password not changed");
        }

        // Return a successful response if no error code is set
        return ResponseEntity.ok(data);
    }
}
