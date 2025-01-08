package kr.co.MyPick_server.Service.Mail;

import kr.co.MyPick_server.DTO.eMail.MailSendReq;

public interface MailServiceImpl {

    /**
     * Sends a verification code email to the specified recipient.
     *
     * @param mailSendReq An object containing the recipient's email address and the verification code.
     *                    The object may also include additional details such as the subject and message body.
     */
    void sendVerificationCodeEmail(MailSendReq mailSendReq);
}
