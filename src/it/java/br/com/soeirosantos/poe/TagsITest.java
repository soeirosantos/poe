package br.com.soeirosantos.poe;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.with;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;

import br.com.soeirosantos.poe.util.JwtToken;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.path.json.JsonPath;
import org.junit.Test;

public class TagsITest {

    private static final String NOTES_RESOURCE = "/api/notes";
    private static final String NOTES_TAGS_RESOURCE = "/api/notes/{id}/tags";

    @Test
    public void
    add_tags_to_note() {
        String body =
            with()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .header(new Header("Authorization", "Bearer " + JwtToken.getToken()))
                .body(getSimpleNoteJsonBody())
                .post(NOTES_RESOURCE).andReturn().asString();

        JsonPath jsonPath = new JsonPath(body);

        given()
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
            .header(new Header("Authorization", "Bearer " + JwtToken.getToken()))
            .body("[{\"name\":\"foo\", \"name\":\"bar\"}]")
            .expect()
            .statusCode(200)
            .when()
            .post(NOTES_TAGS_RESOURCE, jsonPath.getLong("id"));
    }

    @Test
    public void
    get_tags_from_note() {
        String body =
            with()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .header(new Header("Authorization", "Bearer " + JwtToken.getToken()))
                .body(getSimpleNoteJsonBody())
                .post(NOTES_RESOURCE).andReturn().asString();

        JsonPath jsonPath = new JsonPath(body);

        with()
            .contentType(ContentType.JSON)
            .header(new Header("Authorization", "Bearer " + JwtToken.getToken()))
            .body("[{\"name\":\"foo\"}, {\"name\":\"bar\"}]")
            .post(NOTES_TAGS_RESOURCE, jsonPath.getLong("id"));

        given()
            .accept(ContentType.JSON)
            .header(new Header("Authorization", "Bearer " + JwtToken.getToken()))
            .expect()
            .statusCode(200)
            .body("size()", is(2))
            .body("name[0,1]", hasItems("bar", "foo"))
            .when()
            .get(NOTES_TAGS_RESOURCE, jsonPath.getLong("id"));
    }

    @Test
    public void
    delete_tags_from_note() {
        String body =
            with()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .header(new Header("Authorization", "Bearer " + JwtToken.getToken()))
                .body(getSimpleNoteJsonBody())
                .post(NOTES_RESOURCE).andReturn().asString();

        JsonPath jsonPath = new JsonPath(body);

        with()
            .contentType(ContentType.JSON)
            .header(new Header("Authorization", "Bearer " + JwtToken.getToken()))
            .body("[{\"name\":\"foo\"}, {\"name\":\"bar\"}]")
            .post(NOTES_TAGS_RESOURCE, jsonPath.getLong("id"));

        with()
            .contentType(ContentType.JSON)
            .header(new Header("Authorization", "Bearer " + JwtToken.getToken()))
            .body("[{\"name\":\"foo\"}]")
            .delete(NOTES_TAGS_RESOURCE, jsonPath.getLong("id"));

        given()
            .accept(ContentType.JSON)
            .header(new Header("Authorization", "Bearer " + JwtToken.getToken()))
            .expect()
            .statusCode(200)
            .body("size()", is(1))
            .body("name[0]", equalTo("bar"))
            .when()
            .get(NOTES_TAGS_RESOURCE, jsonPath.getLong("id"));
    }

    private String getSimpleNoteJsonBody() {
        return "{\n"
            + "  \"title\": \"FOO\",\n"
            + "  \"content\": {\"body\": \"BAR\"}"
            + " }";
    }
}
