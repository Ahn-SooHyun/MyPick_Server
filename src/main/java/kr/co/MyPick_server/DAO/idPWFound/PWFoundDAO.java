package kr.co.MyPick_server.DAO.idPWFound;

import kr.co.MyPick_server.DTO.eMail.CodeUpdateReq;
import kr.co.MyPick_server.DTO.eMail.MailSendReq;
import kr.co.MyPick_server.DTO.idPWFound.PWChangeReq;
import kr.co.MyPick_server.DTO.idPWFound.PWFoundReq;
import kr.co.MyPick_server.DTO.idPWFound.PWFoundCheckReq;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PWFoundDAO {

    /**
     * Checks if the provided user information matches an existing account
     * for password recovery.
     *
     * @param pwFoundReq An object containing the user's ID, email, or other identifying data.
     * @return Returns 1 if the user exists and matches the given data, otherwise returns 0.
     */
    Integer PWFound(PWFoundReq pwFoundReq);

    /**
     * Saves the generated verification code for password recovery in the database.
     *
     * @param mailSendReq An object containing the email address and the verification code.
     */
    void CodeSave(MailSendReq mailSendReq);

    /**
     * Validates the verification code provided by the user for password recovery.
     *
     * @param pwFoundCheckReq An object containing the verification code and associated data.
     * @return Returns 1 if the code matches the stored data, otherwise returns 0.
     */
    Integer PWFoundCheck(PWFoundCheckReq pwFoundCheckReq);

    /**
     * Updates the verification code data in the database for a specific user or request.
     *
     * @param codeUpdateReq An object containing the updated verification code and related data.
     */
    void PWFoundCheckUpdate(CodeUpdateReq codeUpdateReq);

    /**
     * Updates the user's password in the database.
     *
     * @param pwChangeReq An object containing the user's ID, new password, and verification data.
     * @return Returns 1 if the password was successfully updated, otherwise returns 0.
     */
    int PWChange(PWChangeReq pwChangeReq);
}
