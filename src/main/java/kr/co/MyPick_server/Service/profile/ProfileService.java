package kr.co.MyPick_server.Service.profile;

import kr.co.MyPick_server.DAO.profile.ProfileDAO;
import kr.co.MyPick_server.DTO.profile.UpdateInfoReq;
import kr.co.MyPick_server.DTO.profile.UpdateInfoUpdateReq;
import kr.co.MyPick_server.DTO.profile.UpdateProfileUpdateReq;
import kr.co.MyPick_server.DTO.profile.UserDataDTO;
import kr.co.MyPick_server.Util.BCryptUtil;
import kr.co.MyPick_server.Util.Base64Util;
import kr.co.MyPick_server.Util.ImageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProfileService implements ProfileServiceImpl {

    @Autowired
    private Base64Util base64Util;
    @Autowired
    private BCryptUtil bCryptUtil;
    @Autowired
    private ImageUtil imageUtil;
    @Autowired
    private ProfileDAO profileDAO;

    Logger logger = LoggerFactory.getLogger(ProfileService.class);

    @Override
    public UserDataDTO profileDataGet(int IDX) {
        UserDataDTO userDataDTO = profileDAO.profileDataGet(IDX);

        if (userDataDTO == null) {
            return null;
        }

        userDataDTO.setId(base64Util.decode(userDataDTO.getId()));

        // Fetch and set the Base64-encoded profile image
        String profileImageName = userDataDTO.getProfileImage();
        if (profileImageName != null) {
            try {
                String base64Image = imageUtil.getBase64EncodedImage(profileImageName);
                userDataDTO.setProfileImage(base64Image);
            } catch (Exception e) {
                userDataDTO.setProfileImage(null); // Handle missing or unreadable image
            }
        }

        return userDataDTO;
    }

    @Override
    public int updateInfoCheck(int IDX, String pw) {
        String pwGet = profileDAO.updateInfoCheck(IDX);

        if (!bCryptUtil.checkPassword(base64Util.encode(pw), pwGet)) {
            return 0;
        }

        return 1;
    }

    @Override
    public int updateProfileUpdate(int IDX, MultipartFile profileImage) {
        String fileName;
        try {
            fileName = imageUtil.saveFile(profileImage, IDX);
            // 파일 이름을 사용하여 추가 작업을 수행하거나 결과를 저장
        } catch (Exception e) {
            logger.error("Failed to save profile image", e);
            return 0;
        }

        UpdateProfileUpdateReq updateProfileSetReq = new UpdateProfileUpdateReq();
        updateProfileSetReq.setIDX(IDX);
        updateProfileSetReq.setProfileImage(fileName);

        profileDAO.updateProfileSet(updateProfileSetReq);

        return 1;
    }

    @Override
    public int updateInfoUpdate(int IDX, UpdateInfoReq updateInfoReq) {
        UpdateInfoUpdateReq updateInfoUpdateReq = new UpdateInfoUpdateReq();
        updateInfoUpdateReq.setIDX(IDX);
        updateInfoUpdateReq.setId(base64Util.encode(updateInfoReq.getId()));
        updateInfoUpdateReq.setPw(bCryptUtil.setPassword(base64Util.encode(updateInfoReq.getPw())));
        updateInfoUpdateReq.setName(updateInfoReq.getName());
        updateInfoUpdateReq.setNickName(updateInfoReq.getNickName());
        updateInfoUpdateReq.setBirth(updateInfoReq.getBirth());

        int result = profileDAO.updateInfoUpdate(updateInfoUpdateReq);

        return result;
    }
}
