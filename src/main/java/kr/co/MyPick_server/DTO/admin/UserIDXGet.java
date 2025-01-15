package kr.co.MyPick_server.DTO.admin;

import lombok.Data;

/**
 * UserIDXGet is a Data Transfer Object (DTO) used for retrieving a user's unique identifier (IDX)
 * based on their ID and name. This class is commonly used in administrative operations where
 * a user's details need to be validated or matched against database records.
 */
@Data
public class UserIDXGet {

    /**
     * The user ID associated with the account.
     * This field is used as a key to uniquely identify the user in the system.
     */
    private String id;

    /**
     * The name of the user associated with the account.
     * This field provides additional validation when searching for a user,
     * ensuring that the correct account is targeted in operations.
     */
    private String name;
}
