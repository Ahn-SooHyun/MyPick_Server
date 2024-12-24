package kr.co.MyPick_server.Service.JWT;

import io.jsonwebtoken.Claims;

public interface JWTServiceImpl {

    String createJwt(int IDX);

    Claims validateJwt(String token);

}
