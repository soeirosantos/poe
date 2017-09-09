package br.com.soeirosantos.poe.security.auth.jwt.extractor;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JwtHeaderTokenExtractorTest {

    private static final String TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VybmFtZSIsInNjb3BlcyI6W"
        + "yJST0xFX0FETUlOIl0sImlzcyI6Imh0dHA6Ly9wb2UuY29tLmJyIiwiaWF0IjoxNTA0OTk3OTAwLCJleHAiOjE1M"
        + "DQ5OTg4MDB9.gS-hY4EoxoknbXz1gGFz4LKhOr7Pt6K32l27rChNQcvJcVvL4y85pxV3_EA9jpm5OF00Wy3ZGJVz"
        + "KLobehvhRg";

    @Autowired
    private TokenExtractor tokenExtractor;

    @Test
    public void extract() throws Exception {
        String token = tokenExtractor.extract(TokenExtractor.HEADER_PREFIX + TOKEN);
        assertThat(token).isEqualTo(TOKEN);
    }

    @Test(expected = AuthenticationServiceException.class)
    public void failExtractInvalidHeaderPrefix() throws Exception {
        tokenExtractor.extract("FOO" + TOKEN);
    }

}