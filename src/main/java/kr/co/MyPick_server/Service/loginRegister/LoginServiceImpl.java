package kr.co.MyPick_server.Service.loginRegister;

import kr.co.MyPick_server.DTO.loginReigster.LoginDTO;

public interface LoginServiceImpl {

    LoginDTO login(int IDX);

    int autoLoginCheck(String tocken);

}
