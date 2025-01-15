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

/**
 * AIChatUtil is a utility class that handles interactions with the OpenAI API.
 * It constructs a request to the API, sends it, and then processes the response to map
 * the AI's reply into a ChatMessageDTO object.
 */
@Component
public class AIChatUtil {

    /**
     * The API key used to authenticate requests to the OpenAI API.
     */
    @Value("${openai.api.key}")
    private String apiKey;

    /**
     * The URL endpoint for the OpenAI API. For example:
     * "https://api.openai.com/v1/chat/completions"
     */
    @Value("${openai.api.url}")
    private String apiUrl;

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Sends a chat completion request to the OpenAI API using system and user messages.
     *
     * @param systemMessage The initial context or instruction given to the AI.
     * @param userMessage   The user's query or command.
     * @return A ChatMessageDTO object containing the AI's response, or null if there's an error.
     */
    public ChatMessageDTO sendChat(String systemMessage, String userMessage) {
        // Create a CloseableHttpClient to manage HTTP requests.
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            // Prepare an HTTP POST request with the specified OpenAI endpoint.
            HttpPost request = new HttpPost(apiUrl);
            request.addHeader("Content-Type", "application/json");
            request.addHeader("Authorization", "Bearer " + apiKey);

            // Construct a list of message maps: one for "system", one for "user".
            List<Map<String, String>> messages = List.of(
                    Map.of("role", "system", "content", systemMessage),
                    Map.of("role", "user", "content", userMessage)
            );

            // Prepare the request body as a map.
            // "model" indicates which OpenAI model to use (e.g., "gpt-3.5-turbo", "gpt-4").
            Map<String, Object> body = new HashMap<>();
            body.put("model", "gpt-4o-mini");
            body.put("messages", messages);
            body.put("max_tokens", 16384);
            body.put("temperature", 0.7);

            // Convert the request body to JSON and attach it to the request.
            String jsonBody = objectMapper.writeValueAsString(body);
            request.setEntity(new StringEntity(jsonBody, ContentType.APPLICATION_JSON));

            // Execute the request and process the response.
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                String responseBody = new String(response.getEntity().getContent().readAllBytes());
                System.out.println("OpenAI Response: " + responseBody);

                // Convert the JSON string into a Map for easier parsing.
                Map<String, Object> responseMap = objectMapper.readValue(responseBody, Map.class);

                // Check if the API returned an error object.
                if (responseMap.containsKey("error")) {
                    Map<String, Object> error = (Map<String, Object>) responseMap.get("error");
                    String errorMessage = (String) error.get("message");
                    throw new IllegalStateException("OpenAI API Error: " + errorMessage);
                }

                // Retrieve the "choices" array from the response.
                List<Map<String, Object>> choices = (List<Map<String, Object>>) responseMap.get("choices");
                if (choices == null || choices.isEmpty()) {
                    throw new IllegalStateException("No choices found in OpenAI response: " + responseBody);
                }

                // Extract the first choice's "message" content.
                Map<String, Object> choice = choices.get(0);
                Map<String, Object> message = (Map<String, Object>) choice.get("message");
                String content = (String) message.get("content");
                if (content == null) {
                    throw new IllegalStateException("No content found in the first choice: " + responseBody);
                }

                // Attempt to parse the AI's content into a ChatMessageDTO object.
                try {
                    ChatMessageDTO chatMessageDTO = objectMapper.readValue(content, ChatMessageDTO.class);
                    return chatMessageDTO;
                } catch (IOException e) {
                    e.printStackTrace();
                    // If parsing fails, one could return a fallback or a partial DTO.
                    return null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
