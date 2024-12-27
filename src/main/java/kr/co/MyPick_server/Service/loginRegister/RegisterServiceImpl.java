package kr.co.MyPick_server.Service.loginRegister;

import kr.co.MyPick_server.DTO.loginReigster.RegisterReq;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RegisterServiceImpl {

    int idCheck(String ID);

    int register (RegisterReq registerReq);

}
