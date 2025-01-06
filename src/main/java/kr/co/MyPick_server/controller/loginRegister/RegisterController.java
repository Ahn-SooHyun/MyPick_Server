package kr.co.MyPick_server.controller.loginRegister;

import jakarta.validation.Valid;
import kr.co.MyPick_server.DTO.loginReigster.AutoLoginReq;
import kr.co.MyPick_server.DTO.loginReigster.LoginReq;
import kr.co.MyPick_server.DTO.loginReigster.RegisterReq;
import kr.co.MyPick_server.Service.loginRegister.LoginService;
import kr.co.MyPick_server.Service.loginRegister.RegisterService;
import kr.co.MyPick_server.Util.ResponsData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/register")
public class RegisterController {

    @Autowired
    RegisterService registerService;

    Logger logger = LoggerFactory.getLogger(RegisterController.class);

    @GetMapping("/idCheck")
    public ResponseEntity<?> ID_Check(@RequestParam String ID) {
        ResponsData data = new ResponsData();
        int result = registerService.idCheck(ID);

        if (result != 0) {
            data.setCode("201");
            data.setMessage("ID Error.");
            return ResponseEntity.ok(data);
        }

        return ResponseEntity.ok(data);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterReq registerReq) {
        ResponsData data = new ResponsData();

        int check = registerService.idCheck(registerReq.getId());

        if (check != 0) {
            data.setCode("201");
            data.setMessage("ID Error.");
            return ResponseEntity.ok(data);
        }

        int result = registerService.register(registerReq);

        if (result == 0) {
            data.setCode("401");
            data.setMessage("register does not exist.");
            return ResponseEntity.ok(data);
        }

        data.setMessage("register Success");

        return ResponseEntity.ok(data);
    }

}
