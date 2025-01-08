package kr.co.MyPick_server.Service.idPWFound;

import kr.co.MyPick_server.DTO.eMail.MailSendReq;
import kr.co.MyPick_server.DTO.idPWFound.PWChangeReq;
import kr.co.MyPick_server.DTO.idPWFound.PWFoundReq;
import kr.co.MyPick_server.DTO.idPWFound.PWFoundCheckReq;

public interface PWFoundServerImpl {

    /**
     * Verifies if the provided information matches an existing account for password recovery.
     *
     * @param pwFoundReq An object containing user information such as ID and email.
     * @return Returns 1 if the information matches an existing account, otherwise returns 0.
     */
    int pwFound(PWFoundReq pwFoundReq);

    /**
     * Saves the verification code for password recovery.
     *
     * @param mailSendReq An object containing the email address and the verification code.
     */
    void saveVerificationCode(MailSendReq mailSendReq);

    /**
     * Checks if the provided verification code is valid.
     *
     * @param pwFoundCheckReq An object containing the verification code and related information.
     * @return Returns the verification status as a String, such as "VALID" or "INVALID".
     */
    String pwFoundCheckReq(PWFoundCheckReq pwFoundCheckReq);

    /**
     * Updates the user's password in the system.
     *
     * @param pwChangeReq An object containing the user's ID, new password, and verification details.
     * @return Returns 1 if the password change is successful, otherwise returns 0.
     */
    int pwChange(PWChangeReq pwChangeReq);
}
