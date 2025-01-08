package kr.co.MyPick_server.controller.idPWFound;

import jakarta.validation.Valid;
import kr.co.MyPick_server.DTO.idPWFound.IDFoundReq;
import kr.co.MyPick_server.Service.idPWFound.IDFoundServer;
import kr.co.MyPick_server.Util.ResponsData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The IDFoundController class handles API requests for finding user IDs.
 * It provides an endpoint to search for user IDs based on provided user information.
 */
@RestController
@RequestMapping("/api/Found")
public class IDFoundController {

    @Autowired
    IDFoundServer idFoundServer;

    /**
     * Handles requests to find a user ID based on the provided information.
     *
     * @param idFoundReq The request containing user details (e.g., name, email) for ID retrieval.
     * @return A ResponseEntity containing a ResponsData object with the result of the operation.
     *         If the ID is not found, an error code and message are returned.
     */
    @PostMapping("/idFound")
    public ResponseEntity<?> idFound(@RequestBody @Valid IDFoundReq idFoundReq) {
        ResponsData data = new ResponsData();

        // Call the service to find the user ID
        String result = idFoundServer.idFound(idFoundReq);

        // If no ID is found, return an error response
        if (result == null) {
            data.setCode("401"); // Error code for ID not found
            data.setMessage("ID not found");
            return ResponseEntity.ok(data);
        }

        // If ID is found, set the result in the response
        data.setData(result);

        return ResponseEntity.ok(data);
    }

}
