package kr.co.MyPick_server.DAO.idPWFound;

import kr.co.MyPick_server.DTO.eMail.CodeUpdateReq;
import kr.co.MyPick_server.DTO.eMail.MailSendReq;
import kr.co.MyPick_server.DTO.idPWFound.PWChangeReq;
import kr.co.MyPick_server.DTO.idPWFound.PWFoundReq;
import kr.co.MyPick_server.DTO.idPWFound.PWFoundCheckReq;
import org.apache.ibatis.annotations.Mapper;

/**
 * PWFoundDAO is a Data Access Object (DAO) interface for managing operations related to password recovery.
 * It provides methods for verifying user information, storing and validating recovery codes,
 * and updating user passwords securely.
 */
@Mapper
public interface PWFoundDAO {

    /**
     * Verifies if the provided user information matches an existing account for password recovery purposes.
     *
     * @param pwFoundReq A DTO containing the user's identifying data, such as:
     *                   - ID
     *                   - Name
     *                   - Birthdate
     * @return Returns 1 if the user exists and matches the given data, otherwise returns 0.
     */
    Integer PWFound(PWFoundReq pwFoundReq);

    /**
     * Stores the generated verification code for password recovery in the database.
     * This method ensures that the verification code is saved and can be later validated during recovery.
     *
     * @param mailSendReq A DTO containing:
     *                    - The user's email address
     *                    - The generated verification code
     */
    void CodeSave(MailSendReq mailSendReq);

    /**
     * Validates the verification code provided by the user during the password recovery process.
     *
     * @param pwFoundCheckReq A DTO containing:
     *                        - The verification code
     *                        - Associated user information (e.g., ID or email)
     * @return Returns 1 if the code matches the stored data, otherwise returns 0.
     */
    Integer PWFoundCheck(PWFoundCheckReq pwFoundCheckReq);

    /**
     * Updates the verification code data in the database for a specific user or password recovery request.
     *
     * @param codeUpdateReq A DTO containing:
     *                      - The user's ID
     *                      - The updated verification code
     */
    void PWFoundCheckUpdate(CodeUpdateReq codeUpdateReq);

    /**
     * Updates the user's password in the database after successful verification.
     * This method is called once the user has been authenticated via the recovery process.
     *
     * @param pwChangeReq A DTO containing:
     *                    - The user's ID
     *                    - The new password
     *                    - Verification details
     * @return Returns 1 if the password was successfully updated, otherwise returns 0.
     */
    int PWChange(PWChangeReq pwChangeReq);
}
