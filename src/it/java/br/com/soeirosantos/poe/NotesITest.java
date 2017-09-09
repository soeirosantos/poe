package br.com.soeirosantos.poe;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.with;
import static org.hamcrest.Matchers.equalTo;

import br.com.soeirosantos.poe.util.JwtToken;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.path.json.JsonPath;
import org.junit.Test;

public class NotesITest {

    private static final String NOTES_RESOURCE = "/api/notes";
    private static final String DECRYPT_NOTES_RESOURCE = "/api/notes/{id}/decrypt";
    private static final String NOTES_RESOURCE_SINGLE = "/api/notes/{id}";
    private static final String NOTE_TITLE = "my note";
    private static final String ENCRYPTED_CONTENT = "hAnbjmndH9Nlsovao3KpnBxr5uI+EtaABabjbnFvsp4=";
    private static final String SOME_FAKE_CONTENT_TOKEN = "123";
    private static final String NOTE_CONTENT = "adding some note to my API";
    private static final String X_CONTENT_TOKEN = "X-ContentToken";

    @Test
    public void
    create_a_simple_note() throws Exception {
        given()
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
            .header(new Header("Authorization", "Bearer " + JwtToken.getToken()))
            .body(getSimpleNoteJsonBody())
            .expect()
            .statusCode(201)
            .contentType(ContentType.JSON)
            .body("title", equalTo(NOTE_TITLE))
            .body("content.encrypted", equalTo(false))
            .when()
            .post(NOTES_RESOURCE);
    }

    @Test
    public void
    get_a_simple_note() {
        String body =
            with()
                .accept(ContentType.JSON)
                .header(new Header("Authorization", "Bearer " + JwtToken.getToken()))
                .contentType(ContentType.JSON)
                .body(getSimpleNoteJsonBody())
                .post(NOTES_RESOURCE).andReturn().asString();

        JsonPath jsonPath = new JsonPath(body);

        given()
            .accept(ContentType.JSON)
            .header(new Header("Authorization", "Bearer " + JwtToken.getToken()))
            .contentType(ContentType.JSON)
            .expect()
            .statusCode(200)
            .body("title", equalTo(NOTE_TITLE))
            .when()
            .get(NOTES_RESOURCE_SINGLE, jsonPath.getLong("id"));
    }

    @Test
    public void
    create_encrypted_note() throws Exception {
        given()
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
            .header(new Header("Authorization", "Bearer " + JwtToken.getToken()))
            .header(new Header(X_CONTENT_TOKEN, SOME_FAKE_CONTENT_TOKEN))
            .body(getNoteToEncryptJsonBody())
            .expect()
            .statusCode(201)
            .contentType(ContentType.JSON)
            .body("title", equalTo(NOTE_TITLE))
            .body("content.encrypted", equalTo(true))
            .body("content.body", equalTo(ENCRYPTED_CONTENT))
            .when()
            .post(NOTES_RESOURCE);
    }

    @Test
    public void
    get_decrypted_note() throws Exception {
        String body =
            with()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .header(new Header("Authorization", "Bearer " + JwtToken.getToken()))
                .header(new Header(X_CONTENT_TOKEN, SOME_FAKE_CONTENT_TOKEN))
                .body(getNoteToEncryptJsonBody())
                .post(NOTES_RESOURCE).andReturn().asString();

        JsonPath jsonPath = new JsonPath(body);

        given()
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
            .header(new Header("Authorization", "Bearer " + JwtToken.getToken()))
            .header(new Header(X_CONTENT_TOKEN, SOME_FAKE_CONTENT_TOKEN))
            .expect()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("title", equalTo(NOTE_TITLE))
            .body("content.encrypted", equalTo(Boolean.TRUE))
            .body("content.body", equalTo(NOTE_CONTENT))
            .when()
            .get(DECRYPT_NOTES_RESOURCE, jsonPath.getLong("id"));
    }

    private String getSimpleNoteJsonBody() {
        return "{\n"
            + "  \"title\": \"" + NOTE_TITLE + "\",\n"
            + "  \"content\": {\"body\": \"" + NOTE_CONTENT + "\"}"
            + " }";
    }

    private String getNoteToEncryptJsonBody() {
        return "{\n"
            + "  \"title\": \"" + NOTE_TITLE + "\",\n"
            + "  \"content\": {\"body\": \"" + NOTE_CONTENT
            + "\", \"encrypted\": \"true\"}"
            + " }";
    }

}
