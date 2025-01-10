package kr.co.MyPick_server.Service.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.MyPick_server.DAO.Chat.ChatDAO;
import kr.co.MyPick_server.DTO.chat.CategoryChangeReq;
import kr.co.MyPick_server.DTO.chat.ChatListDTO;
import kr.co.MyPick_server.DTO.chat.ChatReq;
import kr.co.MyPick_server.DTO.chat.ChatRes;
import kr.co.MyPick_server.Util.AIChatUtil;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChatService implements ChatServiceImpl {

    @Autowired
    private ChatDAO chatDAO;

    @Autowired
    private AIChatUtil aiChatUtil;

    Logger logger = LoggerFactory.getLogger(ChatService.class);

    @Override
    public List<ChatListDTO> ChatListGet(int IDX) {
        List<ChatListDTO> chatList = chatDAO.ChatListGet(IDX);
        for (int i = 0; i < chatList.size(); i++) {
            if (chatList.get(i).getSummary() == null) {
                chatList.get(i).setSummary("New Chat");
            }
        }

        return chatList;
    }

    @Override
    public ChatRes getResponse(int IDX, String prompt) {



        return null;

    }



}
