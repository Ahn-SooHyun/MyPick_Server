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
 * GlobalExceptionHandler is a controller advice class for handling exceptions globally
 * across the whole application. It provides centralized exception handling and
 * ensures consistent error responses.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handles the MethodArgumentNotValidException and constructs a response entity with error details.
     *
     * @param ex the MethodArgumentNotValidException thrown when validation on an argument fails
     * @return a ResponseEntity containing a ResponsData object with error details and HTTP status BAD_REQUEST (400)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponsData> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        ResponsData data = new ResponsData();
        data.setCode("500");
        data.setMessage("Validation error");

        String errors = ex.getBindingResult().getAllErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        logger.error(errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(data);
    }
}

