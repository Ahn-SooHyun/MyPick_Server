package kr.co.MyPick_server.controller.idPWFound;

import kr.co.MyPick_server.DTO.idPWFound.IDFoundReq;
import kr.co.MyPick_server.Service.idPWFound.IDFoundServer;
import kr.co.MyPick_server.Util.ResponsData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/Found")
public class IDFound {

    @Autowired
    IDFoundServer idFoundServer;

    @PostMapping("/idFound")
    public ResponseEntity<?> idFound(IDFoundReq idFoundReq) {
        ResponsData data = new ResponsData();

        return ResponseEntity.ok(data);
    }

}
