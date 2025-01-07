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
    public String extractKey(String JWT) {
        try {
            // JWT에서 header를 추출하고 key 복원
            String[] JWTParts = JWT.split("\\.");
            if (JWTParts.length != 3) {
                throw new IllegalArgumentException("Invalid JWT structure");
            }

            // JWT 헤더와 payload는 Base64로 인코딩된 상태이므로 디코딩
            String keyPart = JWTParts[2]; // 서명 부분 추출
            logger.info("Decoded key: " + keyPart);
            return keyPart;
        } catch (Exception e) {
            logger.error("Failed to extract key from JWT: {}", JWT, e);
            throw new IllegalArgumentException("Failed to extract key from JWT", e);
        }
    }



}
