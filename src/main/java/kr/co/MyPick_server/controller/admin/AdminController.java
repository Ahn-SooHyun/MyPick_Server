package kr.co.MyPick_server.controller.admin;

import kr.co.MyPick_server.Service.JWT.JWTService;
import kr.co.MyPick_server.Service.admin.AdminService;
import kr.co.MyPick_server.Util.ResponsData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private JWTService jwtService;
    @Autowired
    private AdminService adminService;

    Logger logger = LoggerFactory.getLogger(AdminController.class);

    @PostMapping("/check")
    public ResponseEntity<?> check(@RequestBody String CT_AT) {
        logger.info("===================================================");
        logger.info("adminCheck");
        logger.info("JWT: {}", CT_AT);
        ResponsData data = new ResponsData();

        // JWT 토큰 검증 로직
        int IDX = jwtService.extractKey(CT_AT);
        if (IDX == -2) {
            data.setCode("509"); // Unauthorized
            data.setMessage("Your account has been suspended.");
            return ResponseEntity.ok(data);
        }
        if (IDX == -1) {
            data.setCode("503");
            data.setMessage("CT_AT does not exist.");
            return ResponseEntity.ok(data);
        }
        if (IDX == 0) {
            data.setCode("504");
            data.setMessage("Your time has expired.");
            return ResponseEntity.ok(data);
        }

        logger.info(String.valueOf(IDX));

        int result = adminService.adminCheck(IDX);
        if (result == 0) {
            data.setCode("590");
            data.setMessage("Your account is not an administrator.");
        }
        else if (result == 1) {
            data.setMessage("Your account is an administrator.");
        }

        return ResponseEntity.ok(data);
    }



}
