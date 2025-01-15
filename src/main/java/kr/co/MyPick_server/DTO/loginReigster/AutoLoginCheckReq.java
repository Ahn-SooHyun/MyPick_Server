package kr.co.MyPick_server.DTO.loginReigster;

import lombok.Data;

import java.util.Date;

/**
 * Data Transfer Object (DTO) used for automatic login verification requests.
 * This DTO carries the necessary information to authenticate a user based on a stored login token.
 */
@Data
public class AutoLoginCheckReq {

    /**
     * The unique index or identifier for the user. This is used to match the token
     * with the user's session or account information.
     */
    private int userIdx;

    /**
     * The timestamp associated with the user's login token. This date is used to verify
     * that the token is still valid and has not expired.
     */
    private Date loginTokenDate;

}
