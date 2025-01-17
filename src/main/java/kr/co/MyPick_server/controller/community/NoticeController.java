package kr.co.MyPick_server.controller.community;

import kr.co.MyPick_server.Util.ResponsData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/community/notice")
public class NoticeController {

    Logger logger = LoggerFactory.getLogger(NoticeController.class);

    @GetMapping("/list")
    public ResponseEntity<?> list() {
        logger.info("===================================================");
        logger.info("profile");
        logger.info("Non");

        // A custom response data object to unify response structure
        ResponsData data = new ResponsData();




        return ResponseEntity.ok(data);
    }

}
