package kr.co.MyPick_server.controller.community;

import kr.co.MyPick_server.Service.community.BoardService;
import kr.co.MyPick_server.Service.community.NoticeService;
import kr.co.MyPick_server.Util.ResponsData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/community/board")
public class BoardController {

    @Autowired
    private BoardService boardService;

    Logger logger = LoggerFactory.getLogger(NoticeController.class);

    @GetMapping("/listMain")
    public ResponseEntity<?> listMain() {
        logger.info("===================================================");
        logger.info("listMain");
        logger.info("Non");

        // A custom response data object to unify response structure
        ResponsData data = new ResponsData();
        data.setData(boardService.listMainGet());

        logger.info(data.toString());
        return ResponseEntity.ok(data);
    }

    @GetMapping("/list")
    public ResponseEntity<?> list() {
        logger.info("===================================================");
        logger.info("profile");
        logger.info("Non");

        // A custom response data object to unify response structure
        ResponsData data = new ResponsData();
        data.setData(boardService.listGet());

        logger.info(data.toString());
        return ResponseEntity.ok(data);
    }
}
