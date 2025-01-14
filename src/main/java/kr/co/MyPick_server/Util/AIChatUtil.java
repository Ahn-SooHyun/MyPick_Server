package kr.co.MyPick_server.Util;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.MyPick_server.DTO.chat.ChatMessage.ChatMessageDTO;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AIChatUtil {

    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.api.url}") // 예: https://api.openai.com/v1/chat/completions
    private String apiUrl;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public ChatMessageDTO sendChat(String systemMessage, String userMessage) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            HttpPost request = new HttpPost(apiUrl);
            request.addHeader("Content-Type", "application/json");
            request.addHeader("Authorization", "Bearer " + apiKey);



            // messages 리스트
            List<Map<String, String>> messages = List.of(
                    Map.of("role", "system", "content", systemMessage),
                    Map.of("role", "user", "content", userMessage)
            );

            // 요청 Body 생성
            Map<String, Object> body = new HashMap<>();
            // 실제 사용 가능한 모델로 교체 필요 (예: "gpt-3.5-turbo" 또는 "gpt-4")
            body.put("model", "gpt-4o-mini");
            body.put("messages", messages);
            body.put("max_tokens", 16384);
            body.put("temperature", 0.7);

            // JSON 직렬화 후 요청에 설정
            String jsonBody = objectMapper.writeValueAsString(body);
            request.setEntity(new StringEntity(jsonBody, ContentType.APPLICATION_JSON));

            // 요청 실행
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                String responseBody = new String(response.getEntity().getContent().readAllBytes());
                System.out.println("OpenAI Response: " + responseBody);

                // 응답 JSON -> Map 변환
                Map<String, Object> responseMap = objectMapper.readValue(responseBody, Map.class);

                // 에러 처리
                if (responseMap.containsKey("error")) {
                    Map<String, Object> error = (Map<String, Object>) responseMap.get("error");
                    String errorMessage = (String) error.get("message");
                    throw new IllegalStateException("OpenAI API Error: " + errorMessage);
                }

                // choices 필드가 있는지 확인
                List<Map<String, Object>> choices = (List<Map<String, Object>>) responseMap.get("choices");
                if (choices == null || choices.isEmpty()) {
                    throw new IllegalStateException("No choices found in OpenAI response: " + responseBody);
                }

                // 첫 번째 choice의 message.content 가져오기
                Map<String, Object> choice = choices.get(0);
                Map<String, Object> message = (Map<String, Object>) choice.get("message");
                String content = (String) message.get("content");
                if (content == null) {
                    throw new IllegalStateException("No content found in the first choice: " + responseBody);
                }

                // AI 응답 content를 ChatDTO 구조로 역직렬화 시도
                try {
                    // content가 예: {"Answer":"...","Summary":"...","List":[...]} 구조라고 가정
                    ChatMessageDTO chatMessageDTO = objectMapper.readValue(content, ChatMessageDTO.class);
                    return chatMessageDTO;
                } catch (IOException e) {
                    e.printStackTrace();
                    // JSON 파싱 실패 시, content 전체를 answer 등으로 담고 싶다면 아래처럼 처리 가능
                    // ChatDTO fallbackDto = new ChatDTO();
                    // fallbackDto.setAnswer(content);
                    // return fallbackDto;

                    return null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
