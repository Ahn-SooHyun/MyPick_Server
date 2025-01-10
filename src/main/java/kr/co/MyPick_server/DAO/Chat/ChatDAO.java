package kr.co.MyPick_server.DAO.Chat;

import kr.co.MyPick_server.DTO.chat.CategoryChangeReq;
import kr.co.MyPick_server.DTO.chat.ChatListDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatDAO {

    List<ChatListDTO> ChatListGet(int IDX);

}
