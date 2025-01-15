package kr.co.MyPick_server.controller.loginRegister;

import jakarta.validation.Valid;
import kr.co.MyPick_server.DTO.loginReigster.RegisterReq;
import kr.co.MyPick_server.Service.loginRegister.RegisterService;
import kr.co.MyPick_server.Util.ResponsData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The RegisterController class manages user registration tasks such as:
 * 1) Checking if a requested user ID is available (not already in use).
 * 2) Validating user details (for example, name and birthdate).
 * 3) Creating a new user account if all validations pass.
 *
 * All responses use a common response format (ResponsData), which includes:
 * - A status or error code (String).
 * - A descriptive message for the outcome.
 * - An optional data payload for successful operations.
 */
@RestController
@RequestMapping("/api/register")
public class RegisterController {

    @Autowired
    RegisterService registerService;

    Logger logger = LoggerFactory.getLogger(RegisterController.class);

    /**
     * Checks whether the given user ID is available for new registration or already in use.
     *
     * Logic Flow:
     * - Receive a user ID as a request parameter.
     * - Call registerService.idCheck(ID) to see if the ID is already registered.
     * - If the result is not 0, the ID is taken. Set code "510" and message "ID Error.".
     * - If the result is 0, the ID is available. Return an empty success response.
     *
     * @param ID The user ID to check.
     * @return A ResponseEntity containing a ResponsData object:
     *         If the ID is taken, code "510" and message "ID Error." are returned.
     *         Otherwise, an empty (successful) response is returned.
     */
    @GetMapping("/idCheck")
    public ResponseEntity<?> idCheck(@RequestParam String ID) {
        logger.info("===================================================");
        logger.info("idCheck - Checking if ID is available");
        logger.info("ID: {}", ID);

        ResponsData data = new ResponsData();

        int result = registerService.idCheck(ID);
        if (result != 0) {
            data.setCode("510");
            data.setMessage("ID Error.");
            return ResponseEntity.ok(data);
        }

        return ResponseEntity.ok(data);
    }

    /**
     * Handles new user registration by checking the requested ID, validating user details,
     * and creating a new account if all checks pass.
     *
     * Logic Flow:
     * - Check if the requested ID is available (via idCheck).
     * - Validate name and birthdate fields (via nameBirthCheck).
     * - If all validations pass, attempt to register the user (via register).
     * - If registration fails at any step, return the corresponding error code ("510", "511", or "512").
     * - If successful, respond with "Register Success".
     *
     * @param registerReq A RegisterReq object containing user details such as:
     *                    - ID
     *                    - Password
     *                    - Name
     *                    - Birthdate
     *                    - Other necessary fields
     * @return A ResponseEntity containing:
     *         - Error codes ("510", "511", "512") with messages if any validation fails.
     *         - A success message ("Register Success") upon a successful registration.
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterReq registerReq) {
        logger.info("===================================================");
        logger.info("register");
        logger.info("registerReq: {}", registerReq);

        ResponsData data = new ResponsData();

        int IDCheck = registerService.idCheck(registerReq.getId());
        if (IDCheck != 0) {
            data.setCode("510");
            data.setMessage("ID Error.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }

        int nameBirthCheck = registerService.nameBirthCheck(registerReq);
        if (nameBirthCheck != 0) {
            data.setCode("511");
            data.setMessage("Name Birth Error.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }

        int result = registerService.register(registerReq);
        if (result == 0) {
            data.setCode("512");
            data.setMessage("Register does not exist.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }

        data.setMessage("Register Success");
        logger.info(data.toString());
        return ResponseEntity.ok(data);
    }
}
