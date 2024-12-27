package kr.co.MyPick_server.Service.loginRegister;

import kr.co.MyPick_server.DTO.loginReigster.LoginDTO;
import kr.co.MyPick_server.DTO.loginReigster.LoginReq;

public interface LoginServiceImpl {

    LoginDTO login(int IDX);

    int autoLoginCheck(String tocken);

    int loginCheck(LoginReq loginReq);

}
