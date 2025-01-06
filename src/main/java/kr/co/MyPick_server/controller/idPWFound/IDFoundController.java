package kr.co.MyPick_server.controller.idPWFound;

import jakarta.validation.Valid;
import kr.co.MyPick_server.DTO.idPWFound.IDFoundReq;
import kr.co.MyPick_server.Service.idPWFound.IDFoundServer;
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
public class IDFoundController {

    @Autowired
    IDFoundServer idFoundServer;

    Logger logger = LoggerFactory.getLogger(IDFoundController.class);

    @PostMapping("/idFound")
    public ResponseEntity<?> idFound(@RequestBody @Valid IDFoundReq idFoundReq) {
        ResponsData data = new ResponsData();

        logger.info(idFoundReq.toString());

        String result = idFoundServer.idFound(idFoundReq);

        if (result == null) {
            data.setCode("401");
            data.setMessage("ID not found");
            return ResponseEntity.ok(data);
        }

        logger.info("Result : " + result);

        data.setData(result);

        return ResponseEntity.ok(data);
    }

}
