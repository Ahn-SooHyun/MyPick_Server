package kr.co.MyPick_server.Service.admin;

import kr.co.MyPick_server.DAO.admin.AdminDAO;
import kr.co.MyPick_server.DAO.admin.AdminMongoDAO;
import kr.co.MyPick_server.DTO.MongoDB.AdminMessageMongoReq;
import kr.co.MyPick_server.DTO.admin.*;
import kr.co.MyPick_server.Util.Base64Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
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

        List<UserListRes> userListRes = adminDAO.UserListGet();
        List<UserListDTO> userListDTO = new ArrayList<>();

        for (int i = 0; i < userListRes.size(); i++) {
            UserListDTO dto = new UserListDTO();

            // 데이터 설정
            userListRes.get(i).setID(base64Util.decode(userListRes.get(i).getID()));

            if ("1".equals(userListRes.get(i).getGeneral())) {
                userListRes.get(i).setGeneral("관리자");
            } else {
                userListRes.get(i).setGeneral("일반 사용자");
            }

            // 계정 정지 날짜 처리
            Timestamp accountSuspension = userListRes.get(i).getAccountSuspension();
            if (accountSuspension != null) {
                if (accountSuspension.toLocalDateTime().toLocalDate().isBefore(LocalDate.now())) {
                    userListRes.get(i).setAccountSuspension(null);
                } else {
                    userListRes.get(i).setAccountSuspension(
                            Timestamp.valueOf(
                                    accountSuspension.toLocalDateTime()
                                            .atZone(ZoneId.of("UTC"))
                                            .withZoneSameInstant(ZoneId.of("Asia/Seoul"))
                                            .toLocalDateTime()
                            )
                    );
                }
            }

            // JWT 토큰 날짜 처리
            Timestamp jwtTokenDate = userListRes.get(i).getJwtTokenDate();
            if (jwtTokenDate != null) {
                userListRes.get(i).setJwtTokenDate(
                        Timestamp.valueOf(
                                jwtTokenDate.toLocalDateTime()
                                        .atZone(ZoneId.of("UTC"))
                                        .withZoneSameInstant(ZoneId.of("Asia/Seoul"))
                                        .toLocalDateTime()
                        )
                );
            }

            // DTO 값 설정
            dto.setName(userListRes.get(i).getName());
            dto.setNickName(userListRes.get(i).getNickName());
            dto.setID(userListRes.get(i).getID());
            dto.setGeneral(userListRes.get(i).getGeneral());
            dto.setAccountSuspension(userListRes.get(i).getAccountSuspension());
            dto.setLastDate(userListRes.get(i).getJwtTokenDate());

            // 리스트에 추가
            userListDTO.add(dto);
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
    public List<UserRoomList> UserRoomGet(int userIDX) {
        return adminDAO.userRoomList(userIDX);
    }

    @Override
    public List<AdminMessageMongoReq> UserMessageGet(int userIDX, int roomNum) {

        List<AdminMessageMongoReq> chats;
        try {
            chats = adminMongoDAO.findByUserIdx(
                    userIDX,
                    roomNum,
                    Sort.by(Sort.Direction.ASC, "date") // 날짜 기준 내림차순 정렬
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
