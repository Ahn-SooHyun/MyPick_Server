package kr.co.MyPick_server.Service.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.MyPick_server.DTO.chat.ChatReq;
import kr.co.MyPick_server.DTO.chat.ChatRes;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.ContentType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChatService implements ChatServiceImpl {

    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.api.url}")
    private String apiUrl; // 예: https://api.openai.com/v1/chat/completions

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public ChatRes getResponse(ChatReq chatReq) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // HttpPost 요청 생성
            HttpPost request = new HttpPost(apiUrl);
            request.addHeader("Content-Type", "application/json");
            request.addHeader("Authorization", "Bearer " + apiKey);

            // 요청 Body 생성
            Map<String, Object> body = new HashMap<>();
            body.put("model", "gpt-4o-mini"); // gpt-4o-mini 모델 사용

            // messages 배열 구성
            List<Map<String, String>> messages = List.of(
                    Map.of("role", "user", "content", chatReq.getPrompt())
            );
            body.put("messages", messages);

            body.put("max_tokens", 150);
            body.put("temperature", 0.7);

            // Body를 JSON으로 변환하여 설정
            StringEntity entity = new StringEntity(objectMapper.writeValueAsString(body), ContentType.APPLICATION_JSON);
            request.setEntity(entity);

            // 요청 실행 및 응답 처리
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                String responseBody = new String(response.getEntity().getContent().readAllBytes());
                System.out.println("OpenAI Response: " + responseBody); // 응답 로그 출력

                Map<String, Object> responseMap = objectMapper.readValue(responseBody, Map.class);

                // 에러 처리
                if (responseMap.containsKey("error")) {
                    Map<String, Object> error = (Map<String, Object>) responseMap.get("error");
                    String errorMessage = (String) error.get("message");
                    throw new IllegalStateException("OpenAI API Error: " + errorMessage);
                }

                // 응답 데이터에서 choices를 안전하게 처리
                List<Map<String, Object>> choices = (List<Map<String, Object>>) responseMap.get("choices");
                if (choices == null || choices.isEmpty()) {
                    throw new IllegalStateException("No choices found in OpenAI response: " + responseBody);
                }

                // 첫 번째 choice에서 message.content 값 추출
                Map<String, Object> choice = choices.get(0);
                Map<String, Object> message = (Map<String, Object>) choice.get("message");
                String text = (String) message.get("content");
                if (text == null) {
                    throw new IllegalStateException("No text found in the first choice: " + responseBody);
                }

                // 응답 DTO 생성
                ChatRes chatRes = new ChatRes();
                chatRes.setResponseText(text.trim());
                return chatRes;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
