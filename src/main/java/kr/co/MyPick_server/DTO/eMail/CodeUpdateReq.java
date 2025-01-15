package kr.co.MyPick_server.DTO.eMail;

import lombok.Data;

/**
 * CodeUpdateReq is a Data Transfer Object (DTO) used for requests to update a security or verification code
 * associated with a user's account. This DTO is commonly utilized in contexts where security codes need refreshing,
 * such as password reset processes or two-factor authentication setups.
 */
@Data
public class CodeUpdateReq {
    /**
     * The unique database index for the user. This index is crucial for identifying the user in the database,
     * ensuring that the code update operation affects the correct user record.
     */
    private int idx;

    /**
     * The user identifier, which could be an email or username. This identifier is used to verify the user's identity
     * and to ensure that the code update is linked to the correct user profile within the system.
     */
    private String id;

    /**
     * The new security or verification code to be saved in the user's account. This code is essential for user
     * authentication processes, such as verifying identity for account access or enabling new security features.
     * The code must be securely generated and stored to prevent unauthorized access.
     */
    private String code;
}
