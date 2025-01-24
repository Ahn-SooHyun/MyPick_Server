package kr.co.MyPick_server.DAO.profile;

import kr.co.MyPick_server.DTO.profile.*;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProfileDAO {

    UserDataDTO profileDataGet(int IDX);

    String updateInfoCheck(int IDX);

    void updateProfileSet(UpdateProfileUpdateReq updateProfileSetReq);

    int updateInfoUpdate(UpdateInfoUpdateReq updateInfoUpdateReq);

    int updatePWUpdate(UpdatePWSetReq updatePWSetReq);

}
