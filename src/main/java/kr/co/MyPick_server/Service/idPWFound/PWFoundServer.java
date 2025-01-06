package kr.co.MyPick_server.Service.idPWFound;

import kr.co.MyPick_server.DAO.idPWFound.PWFoundDAO;
import kr.co.MyPick_server.DTO.eMail.CodeUpdateReq;
import kr.co.MyPick_server.DTO.eMail.MailSendReq;
import kr.co.MyPick_server.DTO.idPWFound.PWChangeReq;
import kr.co.MyPick_server.DTO.idPWFound.PWFoundReq;
import kr.co.MyPick_server.DTO.idPWFound.PWFoundCheckReq;
import kr.co.MyPick_server.Util.BCryptUtil;
import kr.co.MyPick_server.Util.Base64Util;
import kr.co.MyPick_server.Util.RandomCodeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PWFoundServer implements PWFoundServerImpl{

    @Autowired
    private PWFoundDAO pwFoundDAO;

    @Autowired
    private MailService mailService;

    @Autowired
    private RandomCodeUtil randomCodeUtil;

    @Autowired
    private Base64Util base64Util;

    @Autowired
    private BCryptUtil bCryptUtil;

    Logger logger = LoggerFactory.getLogger(PWFoundServer.class);

    @Override
    public int pwFound(PWFoundReq pwFoundReq) {
        pwFoundReq.setId(base64Util.encode(pwFoundReq.getId()));
        Integer userIDX = pwFoundDAO.PWFound(pwFoundReq);
        int result = 0;

        if (userIDX != null) {
            logger.info("User found, generating verification code...");

            MailSendReq mailSendReq = new MailSendReq();

            mailSendReq.setIdx(userIDX);
            mailSendReq.setId(base64Util.decode(pwFoundReq.getId()));
            mailSendReq.setCode(randomCodeUtil.getCode());

            mailService.sendVerificationCodeEmail(mailSendReq); // 메일 발송
            saveVerificationCode(mailSendReq); // 인증 코드 저장

            logger.info("Verification code sent to: " + pwFoundReq.getId());
            result = 1;
        }

        return result;
    }

    public void saveVerificationCode(MailSendReq mailSendReq) {
        pwFoundDAO.CodeSave(mailSendReq);
    }

    @Override
    public String pwFoundCheckReq(PWFoundCheckReq pwFoundCheckReq) {
        Integer userIDX = pwFoundDAO.PWFoundCheck(pwFoundCheckReq);
        String result = null;

        logger.info(userIDX.toString());

        if (userIDX != null) {
            CodeUpdateReq codeUpdateReq = new CodeUpdateReq();
            codeUpdateReq.setId(base64Util.encode(pwFoundCheckReq.getId()));
            codeUpdateReq.setIdx(userIDX);
            codeUpdateReq.setCode(base64Util.encode(randomCodeUtil.getCode()));
            pwFoundDAO.PWFoundCheckUpdate(codeUpdateReq);
            result = codeUpdateReq.getCode();
        }

        return result;
    }

    @Override
    public int pwChange(PWChangeReq pwChangeReq) {
        pwChangeReq.setPw(bCryptUtil.setPassword(base64Util.encode(pwChangeReq.getPw())));
        return pwFoundDAO.PWChange(pwChangeReq);
    }


}
