package kr.co.MyPick_server.DAO.Chat;

import kr.co.MyPick_server.DTO.chat.CategoryChangeReq;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryDAO {

    void categoryChange(CategoryChangeReq categoryChangeReq);

}
