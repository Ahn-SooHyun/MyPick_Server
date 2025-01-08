package kr.co.MyPick_server.controller;

import kr.co.MyPick_server.Util.ResponsData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The test class serves as a simple REST controller for testing purposes.
 * It provides a single endpoint to verify the API's functionality and ensure that
 * the server is running correctly.
 */
@RestController
@RequestMapping("/api")
public class test {

    // Logger instance for logging request and response details
    Logger logger = LoggerFactory.getLogger(test.class);

    /**
     * Handles GET requests to the "/test" endpoint.
     * This method returns a success message and logs the response data.
     *
     * @return A ResponseEntity containing a ResponsData object with a success message and status code 200.
     */
    @GetMapping("/test")
    public ResponseEntity<?> test() {
        // Create a response object
        ResponsData data = new ResponsData();
        data.setCode("200"); // Success code
        data.setMessage("Test Success"); // Success message

        // Log the response data
        logger.info(data.toString());

        // Return the response
        return ResponseEntity.ok(data);
    }
}
