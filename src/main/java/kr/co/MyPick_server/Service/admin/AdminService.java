package kr.co.MyPick_server.Service.admin;

import kr.co.MyPick_server.DAO.admin.AdminDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService implements AdminServiceImpl{

    @Autowired
    private AdminDAO adminDAO;

    @Override
    public int adminCheck(int IDX) {
        return adminDAO.adminCheck(IDX);
    }
}
