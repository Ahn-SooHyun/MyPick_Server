package kr.co.MyPick_server.DAO.loginRegister;

import kr.co.MyPick_server.DTO.loginReigster.LoginDTO;
import kr.co.MyPick_server.DTO.loginReigster.LoginReq;
import kr.co.MyPick_server.DTO.loginReigster.LoginUpdateRes;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface LoginDAO {
    Map<String, Object> autoLogin_Check(String tocken);

    Map<String, Object> login_Check(LoginReq loginReq);

    void login_Update(LoginUpdateRes loginUpdateRes);
}
