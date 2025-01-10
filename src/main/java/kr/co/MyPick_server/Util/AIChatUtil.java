package kr.co.MyPick_server.Util;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.MyPick_server.DTO.chat.ChatDTO;
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

    /**
     * OpenAI Chat API에 요청을 보내고, 응답을 ChatDTO 형태로 반환한다.
     * @param category      : 어떤 카테고리(게임, 영화 등) 추천가인지
     * @param question      : 이번에 사용자가 새로 질문한 내용
     * @param lastQuestion  : 이전에 사용자가 물어봤던 질문
     * @param lastAnswer    : 이전 질문에 대한 AI의 답변
     * @return              : API 응답을 파싱한 ChatDTO
     */
    public ChatDTO sendChat(String category, String question, String lastQuestion, String lastAnswer) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            HttpPost request = new HttpPost(apiUrl);
            request.addHeader("Content-Type", "application/json");
            request.addHeader("Authorization", "Bearer " + apiKey);

            // system 메시지 (category를 문구에 반영)
            String systemMessage = String.format("""
                당신은 "%s"을(를) 추천해주고 리스트를 만들어주는 "%s 추천가"입니다.
                아래의 규칙을 따라야 합니다:

                1. [답변]: [질문]에 대한 답변을 최대 800글자 이내로 작성.
                2. [요약]: [답변]을 최대 30글자로 요약.
                3. [리스트]: [답변]의 리스트를 JSON 형태로 정리.

                출력 예시(반드시 JSON 형식으로 출력):

                {
                    "Answer" : "[답변]은 여기",
                    "Summary" : "[요약]은 여기",
                    "List" : "[리스트]은 여기"
                }

                리스트 상세 예시(배열 등 자유롭게 편성 가능):
                [
                    {
                        "title": "게임 제목 (발매 연도)",
                        "artist": "제작사"
                    },
                    ...
                ]

                다음 형식을 꼭 지키세요.
                """, category, category);

            // user 메시지 (question, lastQuestion, lastAnswer를 문자열에 삽입)
            String userMessage = String.format("""
                [질문]
                %s

                [지난 질문]
                %s

                [지난 답변]
                %s
                """, question, lastQuestion, lastAnswer);

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
            body.put("max_tokens", 150);
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
                    ChatDTO chatDTO = objectMapper.readValue(content, ChatDTO.class);
                    return chatDTO;
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
