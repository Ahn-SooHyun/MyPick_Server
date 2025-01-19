package kr.co.MyPick_server.DAO.community;

import kr.co.MyPick_server.DTO.community.board.BoardListDTO;
import kr.co.MyPick_server.DTO.community.board.BoardMainListDTO;
import kr.co.MyPick_server.DTO.community.notice.NoticeListDTO;
import kr.co.MyPick_server.DTO.community.notice.NoticeMainListDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface BoardDAO {

    List<BoardMainListDTO> listMainGet();

    List<BoardListDTO> listGet();

    List<Map<String, Object>> userName();
}
