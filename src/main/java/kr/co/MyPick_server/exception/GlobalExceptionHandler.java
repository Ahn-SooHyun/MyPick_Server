package kr.co.MyPick_server.exception;

import kr.co.MyPick_server.Util.ResponsData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

/**
 * The GlobalExceptionHandler class provides centralized exception handling for the application.
 * It ensures consistent error responses and simplifies error management across all controllers.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    // Logger for capturing error details
    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handles validation errors caused by invalid method arguments.
     *
     * This method is triggered when a MethodArgumentNotValidException is thrown, typically
     * during validation of request body or parameters. It constructs a custom error response
     * with detailed validation messages.
     *
     * @param ex The MethodArgumentNotValidException thrown when validation on an argument fails.
     * @return A ResponseEntity containing a ResponsData object with error details and
     *         an HTTP status code of BAD_REQUEST (400).
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponsData> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        // Create a response object with error details
        ResponsData data = new ResponsData();
        data.setCode("500"); // Set the error code to indicate failure
        data.setMessage("Validation error"); // General error message

        // Collect all validation error messages
        String errors = ex.getBindingResult().getAllErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        // Log the error details
        logger.error(errors);

        // Return the response entity with BAD_REQUEST status and error details
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(data);
    }
}
