package kr.co.MyPick_server.controller.admin;

import jakarta.validation.Valid;
import kr.co.MyPick_server.DTO.admin.UserReq;
import kr.co.MyPick_server.DTO.admin.UseraccountSuspensionReq;
import kr.co.MyPick_server.Service.JWT.JWTService;
import kr.co.MyPick_server.Service.admin.AdminService;
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
 * AdminController is responsible for handling administrative actions.
 * It provides endpoints for admin-related operations such as user management,
 * account suspension, and privilege management.
 */
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AdminService adminService;

    Logger logger = LoggerFactory.getLogger(AdminController.class);

    /**
     * Checks if the user making the request has administrator privileges.
     *
     * @param CT_AT The JWT token of the user.
     * @return ResponseEntity containing the admin verification result.
     */
    @PostMapping("/check")
    public ResponseEntity<?> check(@RequestBody String CT_AT) {
        logger.info("===================================================");
        logger.info("adminCheck");
        logger.info("JWT: {}", CT_AT);
        ResponsData data = new ResponsData();

        // JWT token validation
        int IDX = jwtService.extractKey(CT_AT);
        if (IDX == -2) {
            data.setCode("509"); // Unauthorized
            data.setMessage("Your account has been suspended.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }
        if (IDX == -1) {
            data.setCode("503");
            data.setMessage("CT_AT does not exist.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }
        if (IDX == 0) {
            data.setCode("504");
            data.setMessage("Your time has expired.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }

        int adminCheck = adminService.adminCheck(IDX);
        if (adminCheck == 0) {
            data.setCode("590");
            data.setMessage("Your account is not an administrator.");
        } else if (adminCheck == 1) {
            data.setMessage("Your account is an administrator.");
        }
        logger.info(data.toString());
        return ResponseEntity.ok(data);
    }

    /**
     * Retrieves the list of users in the system.
     *
     * @param CT_AT The JWT token of the admin.
     * @return ResponseEntity containing the list of users.
     */
    @PostMapping("/userList")
    public ResponseEntity<?> userList(@RequestBody String CT_AT) {
        logger.info("===================================================");
        logger.info("userList");
        logger.info("JWT: {}", CT_AT);
        ResponsData data = new ResponsData();

        // JWT token validation
        int IDX = jwtService.extractKey(CT_AT);
        if (IDX <= 0) {
            data.setCode("50" + (IDX + 9)); // Handles specific errors
            data.setMessage(IDX == -2 ? "Your account has been suspended." :
                    IDX == -1 ? "CT_AT does not exist." :
                            "Your time has expired.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }

        int adminCheck = adminService.adminCheck(IDX);
        if (adminCheck == 0) {
            data.setCode("590");
            data.setMessage("Your account is not an administrator.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }

        data.setData(adminService.UserListGet());
        logger.info(data.toString());
        return ResponseEntity.ok(data);
    }

    /**
     * Retrieves user messages based on the provided user information.
     *
     * @param userReq The request containing user identification data.
     * @return ResponseEntity containing the user's messages.
     */
    @PostMapping("/userMessage")
    public ResponseEntity<?> userMessage(@Valid @RequestBody UserReq userReq) {
        logger.info("===================================================");
        logger.info("userMessage");
        logger.info("UserReq: {}", userReq);
        ResponsData data = new ResponsData();

        // JWT token validation
        int IDX = jwtService.extractKey(userReq.getCT_AT());
        if (IDX <= 0) {
            data.setCode("50" + (IDX + 9));
            data.setMessage(IDX == -2 ? "Your account has been suspended." :
                    IDX == -1 ? "CT_AT does not exist." :
                            "Your time has expired.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }

        int adminCheck = adminService.adminCheck(IDX);
        if (adminCheck == 0) {
            data.setCode("590");
            data.setMessage("Your account is not an administrator.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }

        int userIDX = adminService.UserIDXGet(userReq.getId(), userReq.getName());
        if (userIDX == 0) {
            data.setCode("591");
            data.setMessage("User lookup failed.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }

        data.setData(adminService.UserMessageGet(userIDX));
        logger.info(data.toString());
        return ResponseEntity.ok(data);
    }

    // Similarly detailed comments can be added for other methods like userGeneral, accountSuspension, and userDel.
}
