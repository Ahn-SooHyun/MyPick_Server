package kr.co.MyPick_server.DTO.JWT;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.util.Date;

/**
 * JWTReq class is designed to encapsulate the data required for JWT operations,
 * typically for authentication and authorization purposes.
 * It contains user details that might be encoded into a JWT for secure transmission
 * or for session management.
 */
@Data
public class JWTReq {

    /**
     * Indicates if the user has administrative privileges.
     * 0 might represent a regular user, while 1 could indicate an admin.
     */
    private int admin;

    /**
     * The user's unique identifier. This could be a username or any unique string
     * associated with the user account.
     */
    private String id;

    /**
     * The user's password, necessary for authentication processes. It's included in this DTO
     * for scenarios where a JWT might be created following a direct login attempt.
     */
    private String pw;

    /**
     * The name of the user, which could be included in a JWT to personalize the user's
     * experience in the client application.
     */
    private String name;

    /**
     * The birthdate of the user, potentially used for age verification or personalization.
     */
    private Date birth;

    /**
     * The date and time when the JWT or user session was created.
     * This can be used to determine the validity or expiration of the token.
     */
    private Date dateOfCreation;
}
