package kr.co.MyPick_server.DAO.admin;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminDAO {

    int adminCheck(int IDX);
}
