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
 * The RegisterController class handles user registration and ID duplication checks.
 * It provides endpoints for verifying the uniqueness of user IDs, validating user details,
 * and creating new user accounts.
 */
@RestController
@RequestMapping("/api/register")
public class RegisterController {

    @Autowired
    RegisterService registerService;

    Logger logger = LoggerFactory.getLogger(RegisterController.class);

    /**
     * Checks if the given user ID is already in use.
     *
     * @param ID The user ID to check for duplication.
     * @return A ResponseEntity containing a ResponsData object with the result of the check.
     *         If the ID is already in use, the response includes an error code and message.
     */
    @GetMapping("/idCheck")
    public ResponseEntity<?> idCheck(@RequestParam String ID) {
        logger.info("===================================================");
        logger.info("idCheck");
        logger.info("ID: {}", ID);
        ResponsData data = new ResponsData();

        int result = registerService.idCheck(ID);

        // If ID already exists, return error response
        if (result != 0) {
            data.setCode("201"); // Custom error code for ID duplication
            data.setMessage("ID Error.");
            return ResponseEntity.ok(data);
        }

        // ID is available
        return ResponseEntity.ok(data);
    }

    /**
     * Handles user registration by validating the provided registration details
     * and saving them if the ID and other details are valid.
     *
     * @param registerReq The user registration request containing the required details.
     * @return A ResponseEntity containing a ResponsData object with the registration result.
     *         Returns success message on successful registration or an error message otherwise.
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterReq registerReq) {
        logger.info("===================================================");
        logger.info("register");
        logger.info("registerReq: {}", registerReq);
        ResponsData data = new ResponsData();

        // Check if ID is already in use
        int IDCheck = registerService.idCheck(registerReq.getId());
        if (IDCheck != 0) {
            data.setCode("201"); // Custom error code for ID duplication
            data.setMessage("ID Error.");
            return ResponseEntity.ok(data);
        }

        // Validate name and birthdate
        int nameBirthCheck = registerService.nameBirthCheck(registerReq);
        if (nameBirthCheck != 0) {
            data.setCode("202"); // Custom error code for invalid name or birthdate
            data.setMessage("Name Birth Error.");
            return ResponseEntity.ok(data);
        }

        // Attempt to register the user
        int result = registerService.register(registerReq);

        // Handle registration failure
        if (result == 0) {
            data.setCode("401"); // Custom error code for registration failure
            data.setMessage("Register does not exist.");
            return ResponseEntity.ok(data);
        }

        // Successful registration
        data.setMessage("Register Success");
        return ResponseEntity.ok(data);
    }
}
