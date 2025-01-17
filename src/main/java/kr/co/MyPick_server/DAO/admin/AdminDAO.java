package kr.co.MyPick_server.DAO.admin;

import kr.co.MyPick_server.DTO.admin.UserIDXGet;
import kr.co.MyPick_server.DTO.admin.UserListDTO;
import kr.co.MyPick_server.DTO.admin.UserListRes;
import kr.co.MyPick_server.DTO.admin.UseraccountSuspensionSetReq;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * AdminDAO is a Data Access Object (DAO) interface for managing administrative operations.
 * It provides methods for verifying admin privileges, retrieving user information,
 * and handling account-related tasks such as suspension, privilege updates, and deletion.
 */
@Mapper
public interface AdminDAO {

    /**
     * Checks if the user with the given identifier (IDX) has administrator privileges.
     *
     * @param IDX The unique identifier of the user.
     * @return Returns 1 if the user has admin privileges, otherwise returns 0.
     */
    int adminCheck(int IDX);

    /**
     * Retrieves a list of all users in the system.
     *
     * @return A list of `UserListDTO` objects containing user details, such as:
     *         - Full name
     *         - Nickname
     *         - ID
     *         - Privilege level (e.g., admin or regular user)
     *         - Account suspension status
     */
    List<UserListRes> UserListGet();

    /**
     * Retrieves the unique identifier (IDX) of a user based on their ID and name.
     *
     * @param userIDXGet A DTO containing the user's account ID and name.
     * @return The user's unique identifier (IDX) if a match is found, otherwise returns null.
     */
    Integer UserIDXGet(UserIDXGet userIDXGet);

    /**
     * Updates the privilege level of a user, such as promoting them to an administrator.
     *
     * @param userIDX The unique identifier of the user whose privileges are to be updated.
     * @return Returns 1 if the operation is successful, otherwise returns 0.
     */
    int UserGeneralSet(int userIDX);

    /**
     * Updates the suspension status of a user's account.
     * This method allows administrators to suspend or reactivate accounts by setting a suspension date.
     *
     * @param useraccountSuspensionSetReq A DTO containing the user's unique identifier (IDX)
     *                                    and the suspension timestamp.
     * @return Returns 1 if the suspension status was successfully updated, otherwise returns 0.
     */
    int UserAccountSuspension(UseraccountSuspensionSetReq useraccountSuspensionSetReq);

    /**
     * Deletes a user's account from the system permanently.
     *
     * @param userIDX The unique identifier of the user whose account is to be deleted.
     * @return Returns 1 if the account was successfully deleted, otherwise returns 0.
     */
    int UserDelSet(int userIDX);
}
