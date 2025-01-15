package kr.co.MyPick_server.Service.admin;

import kr.co.MyPick_server.DAO.admin.AdminDAO;
import kr.co.MyPick_server.DAO.admin.AdminMongoDAO;
import kr.co.MyPick_server.DTO.MongoDB.AdminMessageMongoReq;
import kr.co.MyPick_server.DTO.admin.UserIDXGet;
import kr.co.MyPick_server.DTO.admin.UserListDTO;
import kr.co.MyPick_server.DTO.admin.UseraccountSuspensionSetReq;
import kr.co.MyPick_server.Util.Base64Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Service
public class AdminService implements AdminServiceImpl{

    @Autowired
    private Base64Util base64Util;
    @Autowired
    private AdminDAO adminDAO;
    @Autowired
    private AdminMongoDAO adminMongoDAO;

    Logger logger = LoggerFactory.getLogger(AdminService.class);

    @Override
    public int adminCheck(int IDX) {
        return adminDAO.adminCheck(IDX);
    }

    @Override
    public List<UserListDTO> UserListGet() {

        List<UserListDTO> userListDTO = adminDAO.UserListGet();

        for (int i = 0; i < userListDTO.size(); i++) {
            userListDTO.get(i).setID(base64Util.decode(userListDTO.get(i).getID()));

            if (userListDTO.get(i).getGeneral().equals("1")) {
                userListDTO.get(i).setGeneral("관리자");
            }
            else {
                userListDTO.get(i).setGeneral("일반 사용자");
            }

            if (userListDTO.get(i).getAccountSuspension().toLocalDateTime().toLocalDate().isBefore(LocalDate.now())) {
                userListDTO.get(i).setAccountSuspension(null); // 과거 날짜라면 null로 설정
            }
            else {
                userListDTO.get(i).setAccountSuspension(
                        Timestamp.valueOf(
                                userListDTO.get(i).getAccountSuspension()
                                        .toLocalDateTime()
                                        .atZone(ZoneId.of("UTC"))
                                        .withZoneSameInstant(ZoneId.of("Asia/Seoul"))
                                        .toLocalDateTime()
                        )
                );
            }
        }

        return userListDTO;
    }

    @Override
    public int UserIDXGet(String ID, String name) {

        UserIDXGet userIDXGet = new UserIDXGet();
        userIDXGet.setId(base64Util.encode(ID));
        userIDXGet.setName(name);

        Integer userIDX = adminDAO.UserIDXGet(userIDXGet);
        if (userIDX == null) {
            return 0;
        }

        return userIDX;
    }

    @Override
    public List<AdminMessageMongoReq> UserMessageGet(int userIDX) {

        List<AdminMessageMongoReq> chats;
        try {
            chats = adminMongoDAO.findByUserIdx(
                    userIDX,
                    Sort.by(Sort.Direction.DESC, "date") // 날짜 기준 내림차순 정렬
            );

            if (chats == null || chats.isEmpty()) {
                logger.warn("No chats found for UserIDX: {}",
                        1);
                return null;
            }
            logger.info("Chats retrieved successfully: {}", chats.size());
        } catch (Exception e) {
            logger.error("Error while fetching chats: {}", e.getMessage(), e);
            return null;
        }

        return chats;
    }

    @Override
    public int UserGeneralSet(int userIDX) {
        return adminDAO.UserGeneralSet(userIDX);
    }

    @Override
    public int UserAccountSuspension(int userIDX, Timestamp accountSuspension) {
        UseraccountSuspensionSetReq useraccountSuspensionSetReq = new UseraccountSuspensionSetReq();
        useraccountSuspensionSetReq.setIDX(userIDX);
        useraccountSuspensionSetReq.setAccountSuspension(accountSuspension);

        int result = adminDAO.UserAccountSuspension(useraccountSuspensionSetReq);

        return result;
    }

    @Override
    public int UserDelSet(int userIDX) {
        return adminDAO.UserDelSet(userIDX);
    }


}
