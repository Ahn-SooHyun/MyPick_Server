package kr.co.MyPick_server.DAO.loginRegister;

import kr.co.MyPick_server.DTO.loginReigster.LoginReq;
import kr.co.MyPick_server.DTO.loginReigster.LoginUpdateRes;
import kr.co.MyPick_server.DTO.loginReigster.RegisterReq;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface RegisterDAO {

    int idCheck(String ID);

    int register(RegisterReq registerReq);
}
