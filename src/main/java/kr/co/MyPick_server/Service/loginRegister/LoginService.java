package kr.co.MyPick_server.Service.loginRegister;

import kr.co.MyPick_server.DAO.loginRegister.LoginDAO;
import kr.co.MyPick_server.DTO.loginReigster.LoginDTO;
import kr.co.MyPick_server.Service.JWT.JWTService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Service
public class LoginService implements LoginServiceImpl{

    Logger logger = LoggerFactory.getLogger(LoginService.class);

    @Autowired
    LoginDAO loginDAO;

    @Autowired
    JWTService jwtService;

    @Override
    public LoginDTO login(int IDX) {
        LoginDTO loginDTO = new LoginDTO();

        loginDTO.setTocken(UUID.randomUUID());

        Map<String, Object> jwtData = jwtService.createJwt(IDX);

        loginDTO.setJWT((String) jwtData.get("token"));

        logger.info(loginDTO.toString());

        return loginDTO;
    }

    @Override
    public int autoLoginCheck(String tocken) {
        Map<String, Object> result = loginDAO.autoLogin_Check(tocken);

        if (result == null) {
            return -1; // 쿼리 결과가 없을 경우 -1 반환
        }

        // User_IDX 값이 null이거나 존재하지 않는 경우 -1 반환
        Integer IDX = (Integer) result.get("User_IDX");
        if (IDX == null) {
            return -1;
        }

        // UUID_Date 값 변환
        java.sql.Timestamp timestamp = (java.sql.Timestamp) result.get("Login_Token_Date");
        if (timestamp == null) {
            return 0; // Login_Token_Date가 null인 경우 0 반환
        }
        LocalDateTime tokenDate = timestamp.toLocalDateTime(); // Timestamp를 LocalDateTime으로 변환

        // 현재 시간과 Login_Token_Date 간의 차이를 구해 30일 이상 차이나면 0 반환
        if (Duration.between(tokenDate, LocalDateTime.now()).toDays() >= 30) {
            return 0; // 30일 이상 차이나면 0 반환
        }

        return IDX;
    }

}
