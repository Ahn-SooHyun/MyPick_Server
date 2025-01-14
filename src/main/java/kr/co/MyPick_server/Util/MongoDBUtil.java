package kr.co.MyPick_server.Util;

import org.springframework.stereotype.Component;

@Component
public class MongoDBUtil {

    /**
     * 문자열이 null이 아니고 비어 있지 않은지 확인.
     * @param value 검사할 문자열
     * @return 유효한 경우 true, 그렇지 않으면 false
     */
    public boolean isValidString(String value) {
        return value != null && !value.trim().isEmpty();
    }

    /**
     * 숫자가 유효한지 확인 (예: null이 아니고 0 이상인지).
     * @param value 검사할 숫자
     * @return 유효한 경우 true, 그렇지 않으면 false
     */
    public boolean isValidNumber(Number value) {
        return value != null && value.doubleValue() >= 0;
    }

}
