package br.com.soeirosantos.poe.util;

import static io.restassured.RestAssured.with;

import io.restassured.http.ContentType;

public class JwtToken {

    public static String getToken() {
        return with()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                //TODO: extract password to ENV variable
                .body("{\"username\": \"admin@poe.com.br\",\"password\": \"test1234\"}")
                .post("/api/auth/login").jsonPath().getString("token");
    }
}
