package br.com.soeirosantos.poe.security.auth.jwt;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.soeirosantos.poe.security.auth.JwtAuthenticationToken;
import br.com.soeirosantos.poe.security.auth.jwt.extractor.TokenExtractor;
import br.com.soeirosantos.poe.security.domain.entity.Role;
import br.com.soeirosantos.poe.security.model.UserContext;
import br.com.soeirosantos.poe.security.model.token.RawAccessJwtToken;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JwtAuthenticationProviderTest {

    private static final String TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VybmFtZSIsInNjb3BlcyI6W"
        + "yJST0xFX0FETUlOIl0sImlzcyI6Imh0dHA6Ly9wb2UuY29tLmJyIiwiaWF0IjoxNTA0OTk3OTAwLCJleHAiOjE1M"
        + "DQ5OTg4MDB9.gS-hY4EoxoknbXz1gGFz4LKhOr7Pt6K32l27rChNQcvJcVvL4y85pxV3_EA9jpm5OF00Wy3ZGJVz"
        + "KLobehvhRg";

    @Autowired
    private AuthenticationSuccessHandler successHandler;

    @Autowired
    private TokenExtractor tokenExtractor;

    @Autowired
    private JwtAuthenticationProvider authenticationProvider;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void authenticate() throws Exception {
        System.out.println(generateToken());
        RawAccessJwtToken rawToken = new RawAccessJwtToken(generateToken());
        Authentication authentication = new JwtAuthenticationToken(rawToken);
        Authentication authenticated = authenticationProvider.authenticate(authentication);
        assertThat(authenticated.getPrincipal()).isNotNull();
        assertThat(authenticated.getAuthorities()).isNotEmpty();
    }

    //XXX: - improve
    // To test the JwtAuthenticationProvider is necessary a fresh
    // token in order to avoid a token expired exception.
    // To generate a fresh token the following code is necessary.
    // Here we have a concern regarding to use code that is also under test to provide inputs
    private String generateToken() throws IOException, ServletException {
        MockHttpServletResponse response = getResponseWithToken();
        Map<String, String> tokenPart = getJsonAsMap(response);
        return tokenExtractor.extract(TokenExtractor.HEADER_PREFIX + tokenPart.get("token"));
    }

    private MockHttpServletResponse getResponseWithToken() throws IOException, ServletException {
        Authentication authentication = new UsernamePasswordAuthenticationToken(getUserContext(),
            null);
        HttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        successHandler.onAuthenticationSuccess(request, response, authentication);
        return response;
    }

    private Map<String, String> getJsonAsMap(MockHttpServletResponse response)
        throws IOException {
        return objectMapper
            .readValue(response.getContentAsString(), new TypeReference<HashMap<String, String>>() {
            });
    }

    private UserContext getUserContext() {
        GrantedAuthority authority = new SimpleGrantedAuthority(Role.ADMIN.authority());
        return UserContext.create("username", Collections.singletonList(authority));
    }

}