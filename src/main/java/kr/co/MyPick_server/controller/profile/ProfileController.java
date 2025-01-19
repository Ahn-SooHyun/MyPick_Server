package kr.co.MyPick_server.controller.profile;

import jakarta.validation.Valid;
import kr.co.MyPick_server.DTO.profile.UpdateInfoCheckReq;
import kr.co.MyPick_server.DTO.profile.UpdateInfoReq;
import kr.co.MyPick_server.DTO.profile.UpdateProfileReq;
import kr.co.MyPick_server.DTO.profile.UserDataDTO;
import kr.co.MyPick_server.Service.JWT.JWTService;
import kr.co.MyPick_server.Service.profile.ProfileService;
import kr.co.MyPick_server.Util.ResponsData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    private JWTService jwtService;
    @Autowired
    private ProfileService profileService;

    Logger logger = LoggerFactory.getLogger(ProfileController.class);

    @PostMapping("/profile")
    public ResponseEntity<?> profile(@RequestBody String CT_AT) {
        logger.info("===================================================");
        logger.info("profile");
        logger.info("CT_AT : {}", CT_AT);

        // A custom response data object to unify response structure
        ResponsData data = new ResponsData();

        // Extract user IDX (identifier) from the provided JWT token
        int IDX = jwtService.extractKey(CT_AT);
        data.setIdentification(IDX);

        // Check various token validation cases
        if (IDX == -2) {
            // -2 means the account is suspended
            data.setCode("509");
            data.setMessage("Your account has been suspended.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }
        if (IDX == -1) {
            // -1 means the token does not exist
            data.setCode("503");
            data.setMessage("CT_AT does not exist.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }
        if (IDX == 0) {
            // 0 means the token has expired
            data.setCode("504");
            data.setMessage("Your time has expired.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }

        UserDataDTO result = profileService.profileDataGet(IDX);
        if (result == null) {
            data.setCode("580");
            data.setMessage("Your Profile Error.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }

        data.setData(result);

        logger.info(data.toString());
        return ResponseEntity.ok(data);
    }

    @PostMapping("/updateInfoCheck")
    public ResponseEntity<?> updateInfoCheck(@RequestBody @Valid UpdateInfoCheckReq updateInfoCheckReq) {
        logger.info("===================================================");
        logger.info("updateInfoCheck");
        logger.info("UpdateInfoCheckReq : {}", updateInfoCheckReq);

        // A custom response data object to unify response structure
        ResponsData data = new ResponsData();

        // Extract user IDX (identifier) from the provided JWT token
        int IDX = jwtService.extractKey(updateInfoCheckReq.getCT_AT());
        data.setIdentification(IDX);

        // Check various token validation cases
        if (IDX == -2) {
            // -2 means the account is suspended
            data.setCode("509");
            data.setMessage("Your account has been suspended.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }
        if (IDX == -1) {
            // -1 means the token does not exist
            data.setCode("503");
            data.setMessage("CT_AT does not exist.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }
        if (IDX == 0) {
            // 0 means the token has expired
            data.setCode("504");
            data.setMessage("Your time has expired.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }

        int result = profileService.updateInfoCheck(IDX, updateInfoCheckReq.getPw());
        if (result == 0) {
            data.setCode("581");
            data.setMessage("The password is incorrect.");
        }

        data.setMessage("Authentication succeeded.");
        return ResponseEntity.ok(data);
    }

    @PostMapping(path = "/updateProfile", consumes = "multipart/form-data")
    public ResponseEntity<?> updateProfile(@ModelAttribute @Valid UpdateProfileReq updateProfileReq) {
        logger.info("===================================================");
        logger.info("updateProfile");
        logger.info("UpdateInfoReq : {}", updateProfileReq);

        // A custom response data object to unify response structure
        ResponsData data = new ResponsData();

        if (updateProfileReq.getProfileImage().isEmpty()) {
            data.setCode("582");
            data.setMessage("File is empty.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }

        if (updateProfileReq.getProfileImage().getSize() > 1048576) { // 1MB
            data.setCode("583");
            data.setMessage("File size is too large.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }

        // Extract user IDX (identifier) from the provided JWT token
        int IDX = jwtService.extractKey(updateProfileReq.getCT_AT());
        data.setIdentification(IDX);

        // Check various token validation cases
        if (IDX == -2) {
            // -2 means the account is suspended
            data.setCode("509");
            data.setMessage("Your account has been suspended.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }
        if (IDX == -1) {
            // -1 means the token does not exist
            data.setCode("503");
            data.setMessage("CT_AT does not exist.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }
        if (IDX == 0) {
            // 0 means the token has expired
            data.setCode("504");
            data.setMessage("Your time has expired.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }

        int result = profileService.updateProfileUpdate(IDX, updateProfileReq.getProfileImage());
        if (result == 0) {
            data.setCode("584");
            data.setMessage("Failed to upload the profile image.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }

        data.setMessage("Successfully uploaded the profile image.");
        logger.info(data.toString());
        return ResponseEntity.ok(data);
    }

    @PostMapping("/updateInfo")
    public ResponseEntity<?> updateInfo(@RequestBody @Valid UpdateInfoReq updateInfoReq) {
        logger.info("===================================================");
        logger.info("updateInfoCheck");
        logger.info("UpdateInfoReq : {}", updateInfoReq);

        // A custom response data object to unify response structure
        ResponsData data = new ResponsData();

        // Extract user IDX (identifier) from the provided JWT token
        int IDX = jwtService.extractKey(updateInfoReq.getCT_AT());
        data.setIdentification(IDX);

        // Check various token validation cases
        if (IDX == -2) {
            // -2 means the account is suspended
            data.setCode("509");
            data.setMessage("Your account has been suspended.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }
        if (IDX == -1) {
            // -1 means the token does not exist
            data.setCode("503");
            data.setMessage("CT_AT does not exist.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }
        if (IDX == 0) {
            // 0 means the token has expired
            data.setCode("504");
            data.setMessage("Your time has expired.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }

        int result = profileService.updateInfoUpdate(IDX, updateInfoReq);

        if (result == 0) {
            data.setCode("585");
            data.setMessage("Failed to update personal information.");
            logger.info(data.toString());
            return ResponseEntity.ok(data);
        }

        data.setMessage("Personal information update successful.");
        logger.info(data.toString());
        return ResponseEntity.ok(data);
    }



}
