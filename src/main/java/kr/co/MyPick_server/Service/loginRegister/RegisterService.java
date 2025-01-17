package kr.co.MyPick_server.Service.loginRegister;

import kr.co.MyPick_server.DAO.loginRegister.RegisterDAO;
import kr.co.MyPick_server.DTO.loginReigster.RegisterReq;
import kr.co.MyPick_server.Util.BCryptUtil;
import kr.co.MyPick_server.Util.Base64Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterService implements RegisterServiceImpl{

    @Autowired
    Base64Util base64Util;
    @Autowired
    BCryptUtil bCryptUtil;

    @Autowired
    RegisterDAO registerDAO;

    Logger logger = LoggerFactory.getLogger(RegisterService.class);

    @Override
    public int idCheck(String ID) {
        ID = base64Util.encode(ID);

        return registerDAO.idCheck(ID);
    }

    @Override
    public int nameBirthCheck(RegisterReq registerReq) {
        int result = registerDAO.nameBirthCheck(registerReq);
        return result;
    }

    @Override
    public int register(RegisterReq registerReq) {
        logger.info(registerReq.toString());
        registerReq.setId(base64Util.encode(registerReq.getId()));
        registerReq.setPw(bCryptUtil.setPassword(base64Util.encode(registerReq.getPw())));
        registerDAO.register(registerReq);

        return registerDAO.IDXGet(registerReq);
    }
}
