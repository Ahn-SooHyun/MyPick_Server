package kr.co.MyPick_server.Service.JWT;

import kr.co.MyPick_server.DAO.JWT.JWTDAO;
import kr.co.MyPick_server.DTO.JWT.JWTReq;
import kr.co.MyPick_server.Util.Base64Util;
import kr.co.MyPick_server.Util.JWTUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class JWTService implements JWTServiceImpl{

    Logger logger = LoggerFactory.getLogger(JWTService.class);

    @Autowired
    JWTDAO jwtDAO;

    @Autowired
    private JWTUtil jwtUtil; // JWTUtil 객체 주입
    @Autowired
    private Base64Util base64Util;

    @Override
    public Map<String, Object> createJwt(int IDX) {
        JWTReq jwtReq = jwtDAO.JWTdata(IDX);

        if (jwtReq == null) {
            logger.error("Invalid IDX: User not found");
            throw new IllegalArgumentException("Invalid user ID");
        }

        // JWT 생성
        Map<String, Object> result = jwtUtil.generateToken(jwtReq);

        // 결과 반환
        return result;
    }

    @Override
    public Integer extractKey(String JWT) {
        String keyPart = jwtUtil.extractKey(JWT);




        return 0;
    }



}
