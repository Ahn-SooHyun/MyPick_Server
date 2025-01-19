package kr.co.MyPick_server.Service.community;

import kr.co.MyPick_server.DTO.community.board.BoardListDTO;
import kr.co.MyPick_server.DTO.community.board.BoardMainListDTO;
import kr.co.MyPick_server.DTO.community.notice.NoticeListDTO;
import kr.co.MyPick_server.DTO.community.notice.NoticeMainListDTO;

import java.util.List;

public interface BoardServiceImpl {

    List<BoardMainListDTO> listMainGet();

    List<BoardListDTO> listGet();
}
