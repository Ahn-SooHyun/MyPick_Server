package kr.co.MyPick_server.DAO.loginRegister;

import kr.co.MyPick_server.DTO.loginReigster.LoginReq;
import kr.co.MyPick_server.DTO.loginReigster.LoginUpdateRes;
import kr.co.MyPick_server.DTO.loginReigster.RegisterReq;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface RegisterDAO {

    /**
     * Checks if the provided ID already exists in the database.
     *
     * @param ID The user ID to be checked for duplication.
     * @return Returns 1 if the ID already exists, otherwise returns 0.
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
     * Registers a new user in the database with the provided information.
     *
     * @param registerReq An object containing the user's registration information,
     *                    such as ID, password, email, and other necessary details.
     * @return Returns 1 if the registration is successful, otherwise returns 0.
     */
    int register(RegisterReq registerReq);
}
