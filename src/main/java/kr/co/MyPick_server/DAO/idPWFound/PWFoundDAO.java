package kr.co.MyPick_server.DAO.idPWFound;

import kr.co.MyPick_server.DTO.eMail.CodeUpdateReq;
import kr.co.MyPick_server.DTO.eMail.MailSendReq;
import kr.co.MyPick_server.DTO.idPWFound.PWChangeReq;
import kr.co.MyPick_server.DTO.idPWFound.PWFoundReq;
import kr.co.MyPick_server.DTO.idPWFound.PWFoundCheckReq;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PWFoundDAO {
    Integer PWFound(PWFoundReq pwFoundReq);

    void CodeSave(MailSendReq mailSendReq);

    Integer PWFoundCheck(PWFoundCheckReq pwFoundCheckReq);

    void PWFoundCheckUpdate(CodeUpdateReq codeUpdateReq);

    int PWChange(PWChangeReq pwChangeReq);
}
