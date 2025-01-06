package kr.co.MyPick_server.DAO.idPWFound;

import kr.co.MyPick_server.DTO.JWT.JWTReq;
import kr.co.MyPick_server.DTO.idPWFound.IDFoundReq;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IDFoundDAO {
    String IDFound(IDFoundReq idFoundReq);
}
