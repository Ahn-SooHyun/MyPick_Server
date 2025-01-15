package kr.co.MyPick_server.DTO.loginReigster;

import lombok.Data;

/**
 * Data Transfer Object (DTO) used for handling auto-login requests.
 * This class is primarily used to carry the auto-login token that is verified by the system
 * to automatically authenticate a user based on a persisted session.
 */
@Data
public class AutoLoginReq {

    /**
     * The auto-login token that is used to authenticate the user without requiring
     * them to manually enter their credentials again. This token should have been
     * securely stored and issued during a previous successful login attempt.
     */
    private String token;

}
