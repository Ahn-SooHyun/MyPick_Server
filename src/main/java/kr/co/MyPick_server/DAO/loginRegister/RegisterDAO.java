package kr.co.MyPick_server.DAO.loginRegister;

import kr.co.MyPick_server.DTO.loginReigster.LoginReq;
import kr.co.MyPick_server.DTO.loginReigster.LoginUpdateRes;
import kr.co.MyPick_server.DTO.loginReigster.RegisterReq;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * RegisterDAO is a Data Access Object (DAO) interface for managing user registration operations.
 * This interface defines methods for checking user data validity, preventing duplicate registrations,
 * and adding new users to the database.
 */
@Mapper
public interface RegisterDAO {

    /**
     * Checks if the provided user ID already exists in the database.
     * This method is used to prevent duplicate registrations by verifying
     * that the user ID is unique.
     *
     * @param ID The user ID to be checked for duplication.
     * @return Returns 1 if the ID already exists in the database, otherwise returns 0.
     */
    int idCheck(String ID);

    /**
     * Verifies if the provided name and birthdate combination exists in the database.
     * This is typically used as an additional validation step during registration
     * to ensure that the user is providing accurate personal information.
     *
     * @param registerReq A DTO containing the user's name and birthdate to be validated.
     * @return Returns 1 if the name and birthdate combination is valid and exists in the database,
     *         otherwise returns 0.
     */
    int nameBirthCheck(RegisterReq registerReq);

    /**
     * Registers a new user in the database with the provided information.
     * This method saves the user's credentials and other necessary details,
     * such as name, birthdate, and contact information, into the database.
     *
     * @param registerReq A DTO containing the user's registration details, including:
     *                    - ID
     *                    - Password
     *                    - Name
     *                    - Birthdate
     *                    - Email (if applicable)
     * @return Returns 1 if the registration is successful, otherwise returns 0.
     */
    int register(RegisterReq registerReq);

    int IDXGet(RegisterReq registerReq);
}
