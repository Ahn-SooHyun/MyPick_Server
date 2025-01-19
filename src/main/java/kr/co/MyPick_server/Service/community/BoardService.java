package kr.co.MyPick_server.Service.community;

import kr.co.MyPick_server.DAO.community.BoardDAO;
import kr.co.MyPick_server.DAO.community.NoticeDAO;
import kr.co.MyPick_server.DTO.community.board.BoardListDTO;
import kr.co.MyPick_server.DTO.community.board.BoardMainListDTO;
import kr.co.MyPick_server.DTO.community.notice.NoticeListDTO;
import kr.co.MyPick_server.DTO.community.notice.NoticeMainListDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BoardService implements BoardServiceImpl{

    Logger logger = LoggerFactory.getLogger(BoardService.class);

    @Autowired
    private BoardDAO boardDAO;

    @Override
    public List<BoardMainListDTO> listMainGet() {
        return boardDAO.listMainGet();
    }

    @Override
    public List<BoardListDTO> listGet() {
        List<BoardListDTO> list = boardDAO.listGet();
        List<Map<String, Object>> name = boardDAO.userName();

        logger.info(name.toString());

        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < name.size(); j++) {
                // name.get(j).get("User_IDX")를 Integer로 변환 후 String으로 비교
                Integer userIdx = (Integer) name.get(j).get("User_IDX");

                // getEditor()가 String이므로 userIdx를 String으로 변환 후 비교
                if (list.get(i).getEditor() != null && list.get(i).getEditor().equals(userIdx.toString())) {
                    String userName = (String) name.get(j).get("Name");
                    list.get(i).setEditor(userName);
                }
            }
        }
        return list;
    }
}
