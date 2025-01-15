package kr.co.MyPick_server.Service.admin;

import kr.co.MyPick_server.DTO.MongoDB.AdminMessageMongoReq;
import kr.co.MyPick_server.DTO.admin.UserListDTO;

import java.sql.Timestamp;
import java.util.List;

/**
 * AdminServiceImpl is a service interface that defines key administrative operations.
 * It includes functionalities for verifying administrator privileges, managing users,
 * and handling account-related tasks such as suspension, privilege updates, and deletion.
 */
public interface AdminServiceImpl {

    /**
     * Verifies if the user associated with the given identifier (IDX) has administrator privileges.
     *
     * @param IDX The unique identifier of the user.
     * @return Returns 1 if the user has administrator privileges, otherwise returns 0.
     */
    int adminCheck(int IDX);

    /**
     * Fetches a list of all users currently registered in the system.
     *
     * @return A list of `UserListDTO` objects, where each object contains:
     *         - User's name
     *         - Nickname
     *         - ID
     *         - Privilege level (e.g., admin or regular user)
     *         - Account suspension status
     */
    List<UserListDTO> UserListGet();

    /**
     * Retrieves the unique identifier (IDX) of a user based on their account ID and full name.
     *
     * @param ID   The unique account ID of the user.
     * @param name The full name of the user.
     * @return The user's unique identifier (IDX) if a match is found, otherwise returns 0.
     */
    int UserIDXGet(String ID, String name);

    /**
     * Fetches all chat messages associated with a specific user, identified by their IDX.
     *
     * @param userIDX The unique identifier of the user.
     * @return A list of `AdminMessageMongoReq` objects containing details about the user's chat messages,
     *         including timestamps, message content, and metadata.
     */
    List<AdminMessageMongoReq> UserMessageGet(int userIDX);

    /**
     * Updates a user's privilege level, promoting them to an administrator or adjusting their general access rights.
     *
     * @param userIDX The unique identifier of the user whose privileges are being updated.
     * @return Returns 1 if the operation is successful, otherwise returns 0.
     */
    int UserGeneralSet(int userIDX);

    /**
     * Updates the suspension status of a user's account.
     * This can be used to either suspend the account until a specified timestamp or reactivate it by setting the timestamp to null.
     *
     * @param userIDX           The unique identifier of the user whose account suspension status is being updated.
     * @param accountSuspension The suspension timestamp. If null, the account will be reactivated.
     * @return Returns 1 if the account suspension status was successfully updated, otherwise returns 0.
     */
    int UserAccountSuspension(int userIDX, Timestamp accountSuspension);

    /**
     * Permanently deletes a user's account from the system.
     *
     * @param userIDX The unique identifier of the user whose account is to be deleted.
     * @return Returns 1 if the account was successfully deleted, otherwise returns 0.
     */
    int UserDelSet(int userIDX);
}
