package kr.co.MyPick_server.controller.idPWFound;

import jakarta.validation.Valid;
import kr.co.MyPick_server.DTO.idPWFound.PWChangeReq;
import kr.co.MyPick_server.DTO.idPWFound.PWFoundReq;
import kr.co.MyPick_server.DTO.idPWFound.PWFoundCheckReq;
import kr.co.MyPick_server.Service.idPWFound.PWFoundServer;
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
@RequestMapping("/api/Found")
public class PWFoundController {

    @Autowired
    PWFoundServer pwFoundServer;

    Logger logger = LoggerFactory.getLogger(IDFoundController.class);

    @PostMapping("/pwFound")
    public ResponseEntity<?> pwFound(@RequestBody @Valid PWFoundReq pwFoundReq) {
        ResponsData data = new ResponsData();

        int result = pwFoundServer.pwFound(pwFoundReq);

        if (result == 0) {
            data.setCode("201");
            data.setMessage("User not found");
            return ResponseEntity.ok(data);
        }

        return ResponseEntity.ok(data);
    }

    @PostMapping("/pwFoundCheck")
    public ResponseEntity<?> pwFoundCheck(@RequestBody @Valid PWFoundCheckReq pwFoundCheckReq) {
        ResponsData data = new ResponsData();

        String result = pwFoundServer.pwFoundCheckReq(pwFoundCheckReq);

        data.setData(result);

        return ResponseEntity.ok(data);
    }

    @PostMapping("/pwChange")
    public ResponseEntity<?> pwChange(@RequestBody @Valid PWChangeReq pwChangeReq) {
        ResponsData data = new ResponsData();

        int result = pwFoundServer.pwChange(pwChangeReq);

        if (result == 0) {
            data.setCode("201");
            data.setMessage("PW not Changed");
        }

        return ResponseEntity.ok(data);
    }

}
