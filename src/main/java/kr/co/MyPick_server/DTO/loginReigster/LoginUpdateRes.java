package kr.co.MyPick_server.DTO.loginReigster;

import lombok.Data;

/**
 * Data Transfer Object (DTO) used for sending updated login response data back to the client.
 * This includes updates to authentication tokens and user-specific settings or keys.
 */
@Data
public class LoginUpdateRes {

    /**
     * The unique identifier for the user, often used to reference the user
     * in the database and other internal processes.
     */
    private int IDX;

    /**
     * The token used for managing session state or auto-login capabilities.
     * This could be a session token or a similar mechanism that maintains the state of the user's login.
     */
    private String token;

    /**
     * The JSON Web Token (JWT) key used for secure communication between the client and server.
     * JWTs are typically used to ensure that the requests are authenticated and that the payload
     * has not been tampered with during transmission.
     */
    private String JWTKey;

    /**
     * A general-purpose field that can be used to pass any additional information
     * necessary for the client or for the session management.
     */
    private String General;

}
