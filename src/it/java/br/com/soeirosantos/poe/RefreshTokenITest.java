package br.com.soeirosantos.poe;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

import br.com.soeirosantos.poe.util.JwtToken;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import org.junit.Test;

public class RefreshTokenITest {

    protected static final String REFRESH_TOKEN_RESOURCE = "/api/auth/token";

    @Test
    public void get_token_using_refresh_token() {
        given()
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
            .header(new Header("Authorization", "Bearer " + JwtToken.getRefreshToken()))
            .expect()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("token", notNullValue())
            .when()
            .get(REFRESH_TOKEN_RESOURCE);
    }

}
