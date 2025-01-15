package kr.co.MyPick_server.DTO.loginReigster;

import lombok.Data;

import java.util.UUID;

/**
 * Data Transfer Object (DTO) for managing user login sessions.
 * This class facilitates the transmission of session token data following a successful user authentication.
 */
@Data
public class LoginDTO {

    /**
     * The unique token associated with the user's session. This token is typically used
     * to maintain the session state between the client and the server, allowing for
     * continuous authentication over the duration of the user's interaction with the application.
     */
    private UUID token;

    /**
     * A client token (CT_AT), often used in contexts where the client needs to provide a token
     * alongside the session token for additional security measures, such as OAuth2 flows or similar
     * mechanisms that require both client and server verification.
     */
    private String CT_AT;

}
