package kr.co.MyPick_server.controller.loginRegister;

import jakarta.validation.Valid;
import kr.co.MyPick_server.DTO.loginReigster.AutoLoginReq;
import kr.co.MyPick_server.DTO.loginReigster.LoginReq;
import kr.co.MyPick_server.Service.loginRegister.LoginService;
import kr.co.MyPick_server.Util.ResponsData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private LoginService loginService;

    @PostMapping("/autoLogin")
    public ResponseEntity<?> autoLogin(@RequestBody AutoLoginReq autoLoginReq) {
        ResponsData data = new ResponsData();

        int IDX = loginService.autoLoginCheck(autoLoginReq.getTocken());
        if (IDX == -1) {
            data.setCode("401");
            data.setMessage("autoLogin does not exist.");
            return ResponseEntity.ok(data);
        }
        if (IDX == 0) {
            data.setCode("402");
            data.setMessage("Your time has expired.");
            return ResponseEntity.ok(data);
        }

        data.setData(loginService.login(IDX));

        return ResponseEntity.ok(data);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginReq loginReq) {
        ResponsData data = new ResponsData();

        int IDX = loginService.loginCheck(loginReq);
        if (IDX == -1) {
            data.setCode("401");
            data.setMessage("Login does not exist.");
            return ResponseEntity.ok(data);
        }
        if (IDX == 0) {
            data.setCode("402");
            data.setMessage("Your time has expired.");
            return ResponseEntity.ok(data);
        }

        data.setData(loginService.login(IDX));

        return ResponseEntity.ok(data);
    }

}
