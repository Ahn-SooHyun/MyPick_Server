package kr.co.MyPick_server.Service.idPWFound;

import kr.co.MyPick_server.DTO.eMail.MailSendReq;

public interface MailServiceImpl {

    void sendVerificationCodeEmail(MailSendReq mailSendReq);
}
