package kr.co.MyPick_server.DAO.JWT;

import kr.co.MyPick_server.DTO.JWT.JWTReq;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface JWTDAO {
    JWTReq JWTdata(int IDX);

    Map<String, Object> JWTCheck(String extractedKey);

    void JWTDateUpdate(Integer IDX);
}
