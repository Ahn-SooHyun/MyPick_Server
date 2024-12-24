package kr.co.MyPick_server.controller;

import kr.co.MyPick_server.Util.ResponsData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class test {

    Logger logger = LoggerFactory.getLogger(test.class);

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        ResponsData data = new ResponsData();
        data.setCode("200");
        data.setMessage("Test Success");

        logger.info(data.toString());

        return ResponseEntity.ok(data);
    }
}
