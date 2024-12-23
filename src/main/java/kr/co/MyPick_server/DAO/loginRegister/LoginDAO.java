package kr.co.MyPick_server.DAO.loginRegister;

import kr.co.MyPick_server.DTO.loginReigster.AutoLoginRes;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface LoginDAO {

    Map<String, Object> autoLogin_Check(String tocken);
}
