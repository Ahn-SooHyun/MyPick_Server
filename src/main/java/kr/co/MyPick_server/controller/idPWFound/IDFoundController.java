package kr.co.MyPick_server.controller.idPWFound;

import jakarta.validation.Valid;
import kr.co.MyPick_server.DTO.idPWFound.IDFoundReq;
import kr.co.MyPick_server.Service.idPWFound.IDFoundServer;
import kr.co.MyPick_server.Util.ResponsData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The IDFoundController class handles API requests related to finding user IDs.
 * It provides an endpoint to retrieve a user's ID based on the provided details.
 */
@RestController
@RequestMapping("/api/Found")
public class IDFoundController {

    @Autowired
    IDFoundServer idFoundServer;

    Logger logger = LoggerFactory.getLogger(IDFoundController.class);

    /**
     * Endpoint to find a user ID based on user information provided in the request body.
     *
     * @param idFoundReq Contains user details (e.g., name, email) necessary to locate the user ID.
     * @return A ResponseEntity containing a ResponsData object:
     *         - A successful response includes the retrieved ID.
     *         - If no matching ID is found, an error code and message are returned.
     */
    @PostMapping("/idFound")
    public ResponseEntity<?> idFound(@RequestBody @Valid IDFoundReq idFoundReq) {
        logger.info("===================================================");
        logger.info("idFound");
        logger.info("idFoundReq : {}", idFoundReq);

        // Create a standardized response object
        ResponsData data = new ResponsData();

        // Attempt to retrieve the user ID from the service layer
        String result = idFoundServer.idFound(idFoundReq);

        // If no ID could be found, set an appropriate error code and message
        if (result == null) {
            data.setCode("520"); // Custom error code indicating ID not found
            data.setMessage("ID not found");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }

        // If an ID was found, attach it to the response data
        data.setData(result);

        // Return a successful response containing the user ID
        logger.info(data.toString());
        return ResponseEntity.ok(data);
    }
}
