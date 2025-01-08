package kr.co.MyPick_server.Service.JWT;

import kr.co.MyPick_server.DAO.JWT.JWTDAO;
import kr.co.MyPick_server.DTO.JWT.JWTReq;
import kr.co.MyPick_server.Util.Base64Util;
import kr.co.MyPick_server.Util.JWTUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class JWTService implements JWTServiceImpl{

    Logger logger = LoggerFactory.getLogger(JWTService.class);

    @Autowired
    JWTDAO jwtDAO;
    @Autowired
    private JWTUtil jwtUtil; // JWTUtil 객체 주입

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

        Map<String, Object> result = jwtDAO.JWTCheck(keyPart);
        if (result == null) {
            return -1;
        }
        if (!result.get("JWT_Token").equals(keyPart)) {
            return -1;
        }
        // UUID_Date 값 변환
        java.sql.Timestamp timestamp = (java.sql.Timestamp) result.get("JWT_Token_Date");
        if (timestamp == null) {
            return -1; // Login_Token_Date가 null인 경우 0 반환
        }
        LocalDateTime tokenDate = timestamp.toLocalDateTime(); // Timestamp를 LocalDateTime으로 변환
        // 현재 시간과 Login_Token_Date 간의 차이를 구해 30일 이상 차이나면 0 반환
        if (Duration.between(tokenDate, LocalDateTime.now()).toMinutes() >= 20) {
            return 0; // 30일 이상 차이나면 0 반환
        }
        jwtDAO.JWTDateUpdate((Integer) result.get("User_IDX"));

        return (Integer) result.get("User_IDX");
    }



}
