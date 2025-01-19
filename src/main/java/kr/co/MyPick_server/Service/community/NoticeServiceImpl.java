package kr.co.MyPick_server.Service.community;

import kr.co.MyPick_server.DTO.community.notice.NoticeListDTO;
import kr.co.MyPick_server.DTO.community.notice.NoticeMainListDTO;

import java.util.List;

public interface NoticeServiceImpl {

    List<NoticeMainListDTO> listMainGet();

    List<NoticeListDTO> listGet();
}
