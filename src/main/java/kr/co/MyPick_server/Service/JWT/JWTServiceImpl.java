package kr.co.MyPick_server.Service.JWT;

import java.util.Map;

public interface JWTServiceImpl {

    Map<String, Object> createJwt(int IDX);

    Integer extractKey(String JWT);

}
