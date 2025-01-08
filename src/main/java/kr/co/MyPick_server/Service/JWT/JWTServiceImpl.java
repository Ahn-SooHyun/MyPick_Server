package kr.co.MyPick_server.Service.JWT;

import java.util.Map;

public interface JWTServiceImpl {

    /**
     * Generates a JWT token for the specified user or session.
     *
     * @param IDX The unique identifier (ID) of the user or session for which the JWT will be created.
     * @return A map containing the generated JWT token and related metadata, such as expiration time.
     */
    Map<String, Object> createJwt(int IDX);

    /**
     * Extracts the unique key or identifier from a provided JWT token.
     *
     * @param JWT The JSON Web Token (JWT) from which the key will be extracted.
     * @return The extracted key as an Integer, or null if the token is invalid or cannot be parsed.
     */
    Integer extractKey(String JWT);

}
