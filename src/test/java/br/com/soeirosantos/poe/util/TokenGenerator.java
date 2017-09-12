package br.com.soeirosantos.poe.util;

import br.com.soeirosantos.poe.security.auth.jwt.extractor.TokenExtractor;
import br.com.soeirosantos.poe.security.domain.entity.Role;
import br.com.soeirosantos.poe.security.domain.entity.User;
import br.com.soeirosantos.poe.security.model.UserContext;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class TokenGenerator {

    public static final User MOCK_USER_FOR_TOKEN;

    static {
        MOCK_USER_FOR_TOKEN = new User("username", "****");
        MOCK_USER_FOR_TOKEN.addRole(Role.ADMIN);
    }

    @Autowired
    private AuthenticationSuccessHandler successHandler;

    @Autowired
    private TokenExtractor tokenExtractor;

    @Autowired
    private ObjectMapper objectMapper;

    @AllArgsConstructor
    public enum TokenType {
        ACCESS("token"),
        REFRESH("refreshToken");
        private String name;
    }

    //XXX: - improve
    // To test the JwtAuthenticationProvider is necessary a fresh
    // token in order to avoid a token expired exception.
    // To generate a fresh token the following code is necessary.
    // Here we have a concern regarding to use code that is also under test to provide inputs
    public String generateToken(TokenType type) throws IOException, ServletException {
        MockHttpServletResponse response = getResponseWithToken();
        Map<String, String> tokenPart = getJsonAsMap(response);
        return tokenExtractor.extract(TokenExtractor.HEADER_PREFIX + tokenPart.get(type.name));
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
        List<GrantedAuthority> authorities = MOCK_USER_FOR_TOKEN.getRoles().stream()
            .map(r -> new SimpleGrantedAuthority(r.authority())).collect(
                Collectors.toList());
        return UserContext
            .create(MOCK_USER_FOR_TOKEN.getUsername(), authorities);
    }

}