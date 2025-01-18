package kr.co.MyPick_server.interceptor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.MyPick_server.Service.JWT.JWTService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Interceptor implements HandlerInterceptor {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JWTService jwtService;

    // Log index for tracking
    private static final AtomicInteger logIndex = new AtomicInteger(1);

    // 서버 시작 시 로깅
    @PostConstruct
    public void logOnStartup() {
        logger.info("========================================");
        logger.info("Interceptor has been initialized.");
        logger.info("Server startup time: {}", LocalDateTime.now());
        logger.info("========================================");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

//        logger.info("============= construction_api =============");
//        logger.info("Request URI ==> : {}", request.toString());
//
//        logger.info("response URI ==> : {}", response.toString());
//
//        logger.info("handler URI ==> : {}", handler.toString());
//        logger.info("============================================");

        // 여기서 response.sendRedirect("/") 주석 처리를 해제하면 요청을 다른 URL로 리다이렉트할 수 있습니다.
        // response.sendRedirect("/");

        return true;
        // super.preHandle(request, response, handler); 를 사용할 필요가 없습니다.
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (response instanceof ContentCachingResponseWrapper) {
            ContentCachingResponseWrapper wrappedResponse = (ContentCachingResponseWrapper) response;

            // 기존 응답 본문 읽기
            String responseBody = new String(wrappedResponse.getContentAsByteArray(), wrappedResponse.getCharacterEncoding());

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody);

            // identification 값 추출
            String identification = jsonNode.get("identification").asText();

            // identification을 사용해 userIDX 추출
            int userIDX = jwtService.extractKey(identification);

            // identification 필드 제거
            ((ObjectNode) jsonNode).remove("identification");

            // 수정된 JSON을 문자열로 변환
            String modifiedResponseBody = objectMapper.writeValueAsString(jsonNode);

            // 응답 본문에 수정된 데이터를 다시 설정
            wrappedResponse.resetBuffer();
            wrappedResponse.getWriter().write(modifiedResponseBody);

            // 로그 생성
            int index = logIndex.getAndIncrement();
            String ip = request.getRemoteAddr();
            String code = jsonNode.get("code").asText();
            String message = jsonNode.get("message").asText();
            String type = "200".equals(code) ? "SUCCESS" : "ERROR";
            String date = LocalDateTime.now().toString();

            logger.info("Log Record - index: {}, ip: {}, Type: {}, IDX: {}, Code: {}, Message: {}, date: {}",
                    index, ip, type, userIDX, code, message, date);
        } else {
            return;
        }
    }

    public List<String> readLogsFromFile(String filePath) {
        List<String> logs = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                logs.add(line);
            }
        } catch (IOException e) {
            logger.error("Error reading log file: {}", filePath, e);
            return null;
        }
        return logs;
    }

}