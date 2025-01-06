package kr.co.MyPick_server.Service.idPWFound;

import kr.co.MyPick_server.DAO.idPWFound.IDFoundDAO;
import kr.co.MyPick_server.DTO.idPWFound.IDFoundReq;
import kr.co.MyPick_server.Util.Base64Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IDFoundServer implements IDFoundServerImpl{

    @Autowired
    IDFoundDAO idFoundDAO;

    @Autowired
    Base64Util base64Util;

    Logger logger = LoggerFactory.getLogger(IDFoundServer.class);

    @Override
    public String idFound(IDFoundReq idFoundReq) {
        String result = idFoundDAO.IDFound(idFoundReq);

        if (result == null) {
            return null;
        }

        base64Util.decode(result);

        return result;
    }
}
