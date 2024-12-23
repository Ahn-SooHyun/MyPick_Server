package kr.co.MyPick_server.controller.loginRegister;

import kr.co.MyPick_server.Service.loginRegister.LoginService;
import kr.co.MyPick_server.Util.ResponsData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/autoLogin")
    public ResponseEntity<?> autoLogin(@RequestParam String autoLogin) {
        ResponsData data = new ResponsData();

        int IDX = loginService.autoLoginCheck(autoLogin);
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

}
