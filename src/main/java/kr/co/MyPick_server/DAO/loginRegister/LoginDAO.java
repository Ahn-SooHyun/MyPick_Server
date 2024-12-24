package kr.co.MyPick_server.DAO.loginRegister;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface LoginDAO {
    Map<String, Object> autoLogin_Check(String tocken);
}
