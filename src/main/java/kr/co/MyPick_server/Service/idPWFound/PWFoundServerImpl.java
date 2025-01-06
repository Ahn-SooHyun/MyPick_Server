package kr.co.MyPick_server.Service.idPWFound;

import kr.co.MyPick_server.DTO.eMail.MailSendReq;
import kr.co.MyPick_server.DTO.idPWFound.PWChangeReq;
import kr.co.MyPick_server.DTO.idPWFound.PWFoundReq;
import kr.co.MyPick_server.DTO.idPWFound.PWFoundCheckReq;

public interface PWFoundServerImpl {

    int pwFound(PWFoundReq pwFoundReq);

    void saveVerificationCode(MailSendReq mailSendReq);

    String pwFoundCheckReq(PWFoundCheckReq pwFoundCheckReq);

    int pwChange(PWChangeReq pwChangeReq);
}
