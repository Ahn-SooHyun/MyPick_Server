package kr.co.MyPick_server.DAO.JWT;

import kr.co.MyPick_server.DTO.JWT.JWTReq;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface JWTDAO {
    JWTReq JWT_data(int IDX);
}
