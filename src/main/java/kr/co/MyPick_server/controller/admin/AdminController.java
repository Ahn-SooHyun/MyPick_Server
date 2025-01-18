package kr.co.MyPick_server.controller.admin;

import jakarta.validation.Valid;
import kr.co.MyPick_server.DTO.admin.UserReq;
import kr.co.MyPick_server.DTO.admin.UseraccountSuspensionReq;
import kr.co.MyPick_server.Service.JWT.JWTService;
import kr.co.MyPick_server.Service.admin.AdminService;
import kr.co.MyPick_server.Util.ResponsData;
import kr.co.MyPick_server.interceptor.Interceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * AdminController class provides REST API endpoints for administrative operations.
 * It includes features like verifying admin status, managing users, and handling user-related actions.
 * Each endpoint validates the JWT token before performing any operation to ensure security and proper authorization.
 */
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    // Injects JWTService to handle JWT-related functionality, such as token extraction and validation.
    @Autowired
    private JWTService jwtService;

    // Injects AdminService to manage and perform administrative operations like user management.
    @Autowired
    private AdminService adminService;
    @Autowired
    private Interceptor interceptor;

    // Logger to track activity and debugging information for the AdminController.
    private final Logger logger = LoggerFactory.getLogger(AdminController.class);

    /**
     * Checks if the provided JWT token corresponds to a valid admin account.
     *
     * @param CT_AT The JWT token passed in the request body as a string.
     * @return ResponseEntity containing the result of the admin check.
     *         Possible responses:
     *         - Code "509": Account suspended.
     *         - Code "503": JWT token does not exist.
     *         - Code "504": Token expired.
     *         - Code "590": Not an admin account.
     *         - Code "200": Successful admin verification.
     */
    @PostMapping("/check")
    public ResponseEntity<?> check(@RequestBody String CT_AT) {
        logger.info("===================================================");
        logger.info("adminCheck endpoint called.");
        logger.info("JWT: {}", CT_AT);
        ResponsData data = new ResponsData();
        data.setIdentification(CT_AT);

        // Extracts the key (IDX) from the JWT token using the JWTService.
        int IDX = jwtService.extractKey(CT_AT);

        // Handles different cases based on the IDX returned.
        if (IDX == -2) { // Account suspended
            data.setCode("509");
            data.setMessage("Your account has been suspended.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }
        if (IDX == -1) { // Token does not exist
            data.setCode("503");
            data.setMessage("CT_AT does not exist.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }
        if (IDX == 0) { // Token expired
            data.setCode("504");
            data.setMessage("Your time has expired.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }

        // Verifies if the user associated with the token is an administrator.
        int adminCheck = adminService.adminCheck(IDX);
        if (adminCheck == 0) { // Not an admin account
            data.setCode("590");
            data.setMessage("Your account is not an administrator.");
        } else if (adminCheck == 1) { // Admin account verified
            data.setMessage("Your account is an administrator.");
        }
        logger.info(data.toString());
        return ResponseEntity.ok(data);
    }

    /**
     * Retrieves the list of all users for an authenticated admin.
     *
     * @param CT_AT The JWT token passed in the request body as a string.
     * @return ResponseEntity containing the list of users or an appropriate error code.
     *         Possible responses:
     *         - Code "509": Account suspended.
     *         - Code "503": JWT token does not exist.
     *         - Code "504": Token expired.
     *         - Code "590": Not an admin account.
     *         - Code "200": Successfully retrieved user list.
     */
    @PostMapping("/userList")
    public ResponseEntity<?> userList(@RequestBody String CT_AT) {
        logger.info("===================================================");
        logger.info("userList endpoint called.");
        logger.info("JWT: {}", CT_AT);
        ResponsData data = new ResponsData();
        data.setIdentification(CT_AT);

        // Extracts the key (IDX) from the JWT token using the JWTService.
        int IDX = jwtService.extractKey(CT_AT);

        // Handles different cases based on the IDX returned.
        if (IDX == -2) { // Account suspended
            data.setCode("509");
            data.setMessage("Your account has been suspended.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }
        if (IDX == -1) { // Token does not exist
            data.setCode("503");
            data.setMessage("CT_AT does not exist.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }
        if (IDX == 0) { // Token expired
            data.setCode("504");
            data.setMessage("Your time has expired.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }

        // Verifies if the user associated with the token is an administrator.
        int adminCheck = adminService.adminCheck(IDX);
        if (adminCheck == 0) { // Not an admin account
            data.setCode("590");
            data.setMessage("Your account is not an administrator.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }

        // Retrieves the list of users if the token is valid and the user is an admin.
        data.setData(adminService.UserListGet());
        logger.info(data.toString());
        return ResponseEntity.ok(data);
    }

    /**
     * Retrieves a specific user's messages for an authenticated admin.
     *
     * @param userReq A request object containing the user's ID and name, along with the admin's JWT token.
     * @return ResponseEntity containing the user's messages or an appropriate error code.
     *         Possible responses:
     *         - Code "509": Account suspended.
     *         - Code "503": JWT token does not exist.
     *         - Code "504": Token expired.
     *         - Code "590": Not an admin account.
     *         - Code "591": User lookup failed.
     *         - Code "200": Successfully retrieved user messages.
     */
    @PostMapping("/userMessage")
    public ResponseEntity<?> userMessage(@Valid @RequestBody UserReq userReq) {
        logger.info("===================================================");
        logger.info("userMessage endpoint called.");
        logger.info("Request data: {}", userReq);
        ResponsData data = new ResponsData();
        data.setIdentification(userReq.getCT_AT());

        // Extracts the key (IDX) from the JWT token using the JWTService.
        int IDX = jwtService.extractKey(userReq.getCT_AT());

        // Handles different cases based on the IDX returned.
        if (IDX == -2) { // Account suspended
            data.setCode("509");
            data.setMessage("Your account has been suspended.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }
        if (IDX == -1) { // Token does not exist
            data.setCode("503");
            data.setMessage("CT_AT does not exist.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }
        if (IDX == 0) { // Token expired
            data.setCode("504");
            data.setMessage("Your time has expired.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }

        // Verifies if the user associated with the token is an administrator.
        int adminCheck = adminService.adminCheck(IDX);
        if (adminCheck == 0) { // Not an admin account
            data.setCode("590");
            data.setMessage("Your account is not an administrator.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }

        // Fetches the user ID based on the provided user information.
        int userIDX = adminService.UserIDXGet(userReq.getId(), userReq.getName());
        if (userIDX == 0) { // User lookup failed
            data.setCode("591");
            data.setMessage("User lookup failed.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }

        // Retrieves messages for the specified user.
        data.setData(adminService.UserMessageGet(userIDX));
        logger.info(data.toString());
        return ResponseEntity.ok(data);
    }

    /**
     * Changes a user's role to a general user for an authenticated admin.
     *
     * @param userReq A request object containing the user's ID and name, along with the admin's JWT token.
     * @return ResponseEntity containing the result of the role update or an appropriate error code.
     *         Possible responses:
     *         - Code "509": Account suspended.
     *         - Code "503": JWT token does not exist.
     *         - Code "504": Token expired.
     *         - Code "590": Not an admin account.
     *         - Code "591": User lookup failed.
     *         - Code "592": Role update failed.
     *         - Code "200": Role updated successfully.
     */
    @PostMapping("/userGeneral")
    public ResponseEntity<?> userGeneral(@Valid @RequestBody UserReq userReq) {
        logger.info("=================================================");
        logger.info("userGeneral endpoint called.");
        logger.info("Request data: {}", userReq);
        ResponsData data = new ResponsData();
        data.setIdentification(userReq.getCT_AT());

        // Extracts the key (IDX) from the JWT token using the JWTService.
        int IDX = jwtService.extractKey(userReq.getCT_AT());

        // Handles different cases based on the IDX returned.
        if (IDX == -2) { // Account suspended
            data.setCode("509");
            data.setMessage("Your account has been suspended.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }
        if (IDX == -1) { // Token does not exist
            data.setCode("503");
            data.setMessage("CT_AT does not exist.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }
        if (IDX == 0) { // Token expired
            data.setCode("504");
            data.setMessage("Your time has expired.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }

        // Verifies if the user associated with the token is an administrator.
        int adminCheck = adminService.adminCheck(IDX);
        if (adminCheck == 0) { // Not an admin account
            data.setCode("590");
            data.setMessage("Your account is not an administrator.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }

        // Fetches the user ID based on the provided user information.
        int userIDX = adminService.UserIDXGet(userReq.getId(), userReq.getName());
        if (userIDX == 0) { // User lookup failed
            data.setCode("591");
            data.setMessage("User lookup failed.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }

        // Updates the user's role to general user.
        int result = adminService.UserGeneralSet(userIDX);
        if (result == 0) { // Role update failed
            data.setCode("592");
            data.setMessage("User general failed.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }

        data.setMessage("User general success.");
        logger.info(data.toString());
        return ResponseEntity.ok(data);
    }

    /**
     * Suspends or reactivates a user's account for an authenticated admin.
     *
     * @param useraccountSuspensionReq A request object containing the user's ID, name, and suspension status.
     * @return ResponseEntity containing the result of the account suspension operation or an appropriate error code.
     *         Possible responses:
     *         - Code "509": Account suspended.
     *         - Code "503": JWT token does not exist.
     *         - Code "504": Token expired.
     *         - Code "590": Not an admin account.
     *         - Code "591": User lookup failed.
     *         - Code "593": Account suspension operation failed.
     *         - Code "200": Account suspension operation successful.
     */
    @PostMapping("/accountSuspension")
    public ResponseEntity<?> accountSuspension(@Valid @RequestBody UseraccountSuspensionReq useraccountSuspensionReq) {
        logger.info("=================================================");
        logger.info("accountSuspension endpoint called.");
        logger.info("Request data: {}", useraccountSuspensionReq);
        ResponsData data = new ResponsData();
        data.setIdentification(useraccountSuspensionReq.getCT_AT());

        // Extracts the key (IDX) from the JWT token using the JWTService.
        int IDX = jwtService.extractKey(useraccountSuspensionReq.getCT_AT());

        // Handles different cases based on the IDX returned.
        if (IDX == -2) { // Account suspended
            data.setCode("509");
            data.setMessage("Your account has been suspended.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }
        if (IDX == -1) { // Token does not exist
            data.setCode("503");
            data.setMessage("CT_AT does not exist.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }
        if (IDX == 0) { // Token expired
            data.setCode("504");
            data.setMessage("Your time has expired.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }

        // Verifies if the user associated with the token is an administrator.
        int adminCheck = adminService.adminCheck(IDX);
        if (adminCheck == 0) { // Not an admin account
            data.setCode("590");
            data.setMessage("Your account is not an administrator.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }

        // Fetches the user ID based on the provided user information.
        int userIDX = adminService.UserIDXGet(useraccountSuspensionReq.getId(), useraccountSuspensionReq.getName());
        if (userIDX == 0) { // User lookup failed
            data.setCode("591");
            data.setMessage("User lookup failed.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }

        // Suspends or reactivates the user's account based on the provided status.
        int result = adminService.UserAccountSuspension(userIDX, useraccountSuspensionReq.getAccountSuspension());
        if (result == 0) { // Account suspension operation failed
            data.setCode("593");
            data.setMessage("Account suspension failed.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }

        data.setData(useraccountSuspensionReq.getAccountSuspension());
        logger.info(data.toString());
        return ResponseEntity.ok(data);
    }

    /**
     * Deletes a user from the system for an authenticated admin.
     *
     * @param userReq A request object containing the user's ID and name, along with the admin's JWT token.
     * @return ResponseEntity containing the result of the deletion operation or an appropriate error code.
     *         Possible responses:
     *         - Code "509": Account suspended.
     *         - Code "503": JWT token does not exist.
     *         - Code "504": Token expired.
     *         - Code "590": Not an admin account.
     *         - Code "591": User lookup failed.
     *         - Code "592": User deletion failed.
     *         - Code "200": User deleted successfully.
     */
    @PostMapping("/userDel")
    public ResponseEntity<?> userDel(@Valid @RequestBody UserReq userReq) {
        logger.info("=================================================");
        logger.info("userDel endpoint called.");
        logger.info("UserReq: {}", userReq);
        ResponsData data = new ResponsData();
        data.setIdentification(userReq.getCT_AT());

        // Extracts the key (IDX) from the JWT token using the JWTService.
        int IDX = jwtService.extractKey(userReq.getCT_AT());

        // Handles different cases based on the IDX returned.
        if (IDX == -2) { // Account suspended
            data.setCode("509");
            data.setMessage("Your account has been suspended.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }
        if (IDX == -1) { // Token does not exist
            data.setCode("503");
            data.setMessage("CT_AT does not exist.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }
        if (IDX == 0) { // Token expired
            data.setCode("504");
            data.setMessage("Your time has expired.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }

        // Verifies if the user associated with the token is an administrator.
        int adminCheck = adminService.adminCheck(IDX);
        if (adminCheck == 0) { // Not an admin account
            data.setCode("590");
            data.setMessage("Your account is not an administrator.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }

        // Fetches the user ID based on the provided user information.
        int userIDX = adminService.UserIDXGet(userReq.getId(), userReq.getName());
        if (userIDX == 0) { // User lookup failed
            data.setCode("591");
            data.setMessage("User lookup failed.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }

        // Deletes the user from the system.
        int result = adminService.UserDelSet(userIDX);
        if (result == 0) { // Deletion operation failed
            data.setCode("592");
            data.setMessage("User Delete failed.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }

        data.setMessage("User Delete success.");
        logger.info(data.toString());
        return ResponseEntity.ok(data);
    }

    @PostMapping("log")
    public ResponseEntity<?> log(@RequestBody String CT_AT) {
        logger.info("=================================================");
        logger.info("userDel endpoint called.");
        logger.info("CT_AT: {}", CT_AT);
        ResponsData data = new ResponsData();
        data.setIdentification(CT_AT);

        // Extracts the key (IDX) from the JWT token using the JWTService.
        int IDX = jwtService.extractKey(CT_AT);

        // Handles different cases based on the IDX returned.
        if (IDX == -2) { // Account suspended
            data.setCode("509");
            data.setMessage("Your account has been suspended.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }
        if (IDX == -1) { // Token does not exist
            data.setCode("503");
            data.setMessage("CT_AT does not exist.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }
        if (IDX == 0) { // Token expired
            data.setCode("504");
            data.setMessage("Your time has expired.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }

        // Verifies if the user associated with the token is an administrator.
        int adminCheck = adminService.adminCheck(IDX);
        if (adminCheck == 0) { // Not an admin account
            data.setCode("590");
            data.setMessage("Your account is not an administrator.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }

        // Fetch logs from Interceptor and set to data
        List<String> logs = interceptor.readLogsFromFile("logs/interceptor.log");
        if (logs == null) {
            data.setCode("599");
            data.setMessage("No log records found.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }

        data.setData(logs);
        data.setMessage("Logs retrieved successfully.");
        logger.info(data.toString());
        return ResponseEntity.ok(data);
    }
}
