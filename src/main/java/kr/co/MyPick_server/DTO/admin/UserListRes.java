package kr.co.MyPick_server.DTO.admin;

import lombok.Data;

import java.sql.Timestamp;

/**
 * UserListDTO is a Data Transfer Object (DTO) used for representing user information
 * in an administrative context. This class is commonly used to display a list of users
 * and their details in administrative tools or dashboards.
 */
@Data
public class UserListRes {

    /**
     * The full name of the user.
     * This field is used for identifying users in a formal or administrative context.
     */
    private String name;

    /**
     * The nickname of the user.
     * This field represents a less formal, user-chosen name, often used for display purposes
     * in non-administrative interfaces.
     */
    private String nickName;

    /**
     * The unique identifier (ID) of the user.
     * This field is used as a key to uniquely identify the user in the system.
     */
    private String ID;

    /**
     * The general role or privilege level of the user.
     * Common values:
     * - "1" indicates the user is an administrator.
     * - "0" indicates the user is a regular user.
     */
    private String general;

    /**
     * The timestamp indicating when the user's account is suspended until.
     * If this field is null, the account is active and not currently suspended.
     * If it contains a value, it represents the date and time when the suspension will end.
     */
    private Timestamp accountSuspension;

    private Timestamp jwtTokenDate;
}
