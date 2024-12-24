package kr.co.MyPick_server.Service.JWT;

import io.jsonwebtoken.Claims;
import kr.co.MyPick_server.DAO.JWT.JWTDAO;
import kr.co.MyPick_server.DTO.JWT.JWTReq;
import kr.co.MyPick_server.Util.JWTUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Service
public class JWTService implements JWTServiceImpl{

    Logger logger = LoggerFactory.getLogger(JWTService.class);

    @Autowired
    JWTDAO jwtDAO;

    @Override
    public String createJwt(int IDX) {
        JWTReq jwtReq = jwtDAO.JWT_data(IDX);

        logger.info(jwtReq.toString());

        // JWT 생성
        String token = new JWTUtil.generateToken(jwtReq); // 비정적 메서드 호출

        // 생성된 토큰 로그 출력
        logger.info("Generated Token: {}", token);

        // 토큰 반환
        return token;
    }

    @Override
    public Claims validateJwt(String token) {
        return null;
    }
}
