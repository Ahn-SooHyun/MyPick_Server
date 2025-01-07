package kr.co.MyPick_server.DAO.loginRegister;

import kr.co.MyPick_server.DTO.loginReigster.LoginReq;
import kr.co.MyPick_server.DTO.loginReigster.LoginUpdateRes;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface LoginDAO {
    Map<String, Object> autoLoginCheck(String tocken);

    Map<String, Object> loginCheck(LoginReq loginReq);

    void loginUpdate(LoginUpdateRes loginUpdateRes);
}
