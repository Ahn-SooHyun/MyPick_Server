package kr.co.MyPick_server.interceptor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.MyPick_server.Service.JWT.JWTService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.ContentCachingResponseWrapper;

public class Interceptor implements HandlerInterceptor {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JWTService jwtService;

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
        JsonNode jsonNode = null;
        if (response instanceof ContentCachingResponseWrapper) {
            ContentCachingResponseWrapper wrappedResponse = (ContentCachingResponseWrapper) response;

            // 응답 본문 추출
            String responseBody = new String(wrappedResponse.getContentAsByteArray(), wrappedResponse.getCharacterEncoding());

            ObjectMapper objectMapper = new ObjectMapper();
            jsonNode = objectMapper.readTree(responseBody);
        } else {
            logger.warn("Response is not instance of ContentCachingResponseWrapper");
        }

        int index = 1; // 순차적 증가를 위한 index
        String ip = request.getRemoteAddr(); // 클라이언트의 IP 주소
        String identification = jsonNode.get("identification").asText(); // 기본값
        int userIDX = jwtService.extractKey(identification);
        String code = jsonNode.get("code").asText(); // 기본값
        String message = jsonNode.get("message").asText(); // 기본값
        String type = "Unknown"; // 기본값
        String date = java.time.LocalDateTime.now().toString(); // 현재 날짜와 시간


        // 로그 타입 설정
        type = "200".equals(code) ? "SUCCESS" : "ERROR";

        // 로그 출력
        logger.info("Log Record - index: {}, ip: {}, Type: {}, IDX: {}, Code: {}, Message: {}, date: {}",
                index, ip, type, userIDX, code, message, date);
    }



}
