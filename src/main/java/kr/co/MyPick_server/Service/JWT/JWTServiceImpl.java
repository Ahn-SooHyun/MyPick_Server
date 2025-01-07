package kr.co.MyPick_server.Service.JWT;

import io.jsonwebtoken.Claims;

import java.util.Map;

public interface JWTServiceImpl {

    Map<String, Object> createJwt(int IDX);

    String extractKey(String JWT);

}
