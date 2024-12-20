package kr.co.MyPick_server.Util;

import lombok.Data;

/**
 * The ResponsData class is a data structure for representing response messages in a standardized way.
 * It contains fields for a status code, a message, and an optional data payload.
 *
 * The default constructor initializes the object with a success code and message.
 */
@Data
public class ResponsData {

    private String code; //성공:200, 실패: 500.
    private String message; //에러내역 ( properties에서 관리 )

    private Object data; // response 값을 담는다.

    /**
     * Initializes a new instance of the ResponsData class with default values.
     * Sets the response code to "200" indicating success and the message to "success".
     */
    //default 설정 성공
    public ResponsData() {
        this.code = "200"; //성공 코드
        this.message = "success";
    }
}
