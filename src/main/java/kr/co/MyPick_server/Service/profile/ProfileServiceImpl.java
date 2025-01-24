package kr.co.MyPick_server.Service.profile;

import kr.co.MyPick_server.DTO.profile.UpdateInfoReq;
import kr.co.MyPick_server.DTO.profile.UserDataDTO;
import org.springframework.web.multipart.MultipartFile;

public interface ProfileServiceImpl {

    UserDataDTO profileDataGet(int IDX);

    int updateInfoCheck(int IDX, String pw);

    int updateProfileUpdate(int IDX, MultipartFile profileImage);

    int updateInfoUpdate(int IDX, UpdateInfoReq updateInfoReq);

    int updatePWUpdate(int IDX, String oldPW, String newPW);
}
