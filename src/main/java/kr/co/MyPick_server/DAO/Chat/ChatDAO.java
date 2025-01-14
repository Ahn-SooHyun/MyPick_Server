package kr.co.MyPick_server.DAO.Chat;

import kr.co.MyPick_server.DTO.MongoDB.ChatMessageMongoReq;
import kr.co.MyPick_server.DTO.chat.ChatRoom.ChatRoomListDTO;
import kr.co.MyPick_server.DTO.chat.ChatRoom.ChatRoomCreateReq;
import kr.co.MyPick_server.DTO.chat.ChatMessageList.ChatMessageListGetReq;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatDAO {

    List<ChatRoomListDTO> ChatRoomListGet(int IDX);

    void ChatRoomCreate(ChatRoomCreateReq ChatRoomCreateReq);

    int ChatRoomLastIDXGet(int IDX);

    String ChatRoomCategoryGet(int chatIDX);

    Integer ChatRoomIDXGet(ChatMessageListGetReq chatMessageListGetReq);

    void ChatRoomUpdate(ChatMessageMongoReq chatMessageMongoReq);

    int ChatRoomDelete(ChatMessageListGetReq chatMessageListGetReq);

}
