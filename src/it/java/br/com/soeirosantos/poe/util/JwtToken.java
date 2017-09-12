package br.com.soeirosantos.poe.util;

import static io.restassured.RestAssured.with;

import io.restassured.http.ContentType;

public class JwtToken {

    //TODO: extract password to ENV variable
    private static final String USER_JSON =
        "{\"username\": \"admin@poe.com.br\",\"password\": \"test1234\"}";
    private static final String API_AUTH_LOGIN = "/api/auth/login";

    public static String getToken() {
        return with()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(USER_JSON)
                .post(API_AUTH_LOGIN).jsonPath().getString("token");
    }

    public static String getRefreshToken() {
        return with()
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
            .body(USER_JSON)
            .post(API_AUTH_LOGIN).jsonPath().getString("refreshToken");
    }
}
