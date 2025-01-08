package kr.co.MyPick_server.Service.loginRegister;

import kr.co.MyPick_server.DTO.loginReigster.RegisterReq;
import org.apache.ibatis.annotations.Mapper;

/**
 * The RegisterServiceImpl interface provides methods for user registration
 * and validation processes, such as checking ID duplication and verifying name and birthdate.
 */
@Mapper
public interface RegisterServiceImpl {

    /**
     * Checks whether the provided user ID already exists in the database.
     *
     * @param ID The user ID to be checked for duplication.
     * @return Returns 1 if the ID is already in use, otherwise returns 0.
     */
    int idCheck(String ID);

    /**
     * Verifies if the provided name and birthdate combination exists in the database.
     *
     * @param registerReq An object containing the name and birthdate to be validated.
     * @return Returns 1 if the combination is valid, otherwise returns 0.
     */
    int nameBirthCheck(RegisterReq registerReq);

    /**
     * Handles the process of registering a new user with the provided information.
     *
     * @param registerReq An object containing the registration details such as
     *                    ID, password, email, and other necessary data.
     * @return Returns 1 if the registration is successful, otherwise returns 0.
     */
    int register(RegisterReq registerReq);

}
