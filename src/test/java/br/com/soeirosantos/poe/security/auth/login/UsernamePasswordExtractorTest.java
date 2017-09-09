package br.com.soeirosantos.poe.security.auth.login;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import br.com.soeirosantos.poe.security.auth.login.extractor.AuthenticationExtractor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsernamePasswordExtractorTest {

    private static final String USERNAME = "foo";
    private static final String PASSWORD = "bar";

    @Autowired
    private AuthenticationExtractor authenticationExtractor;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void extract() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        given(request.getMethod()).willReturn(HttpMethod.POST.name());
        given(request.getReader()).willReturn(getLoginRequest());
        Authentication authentication =
            authenticationExtractor.extract(request);
        assertThat(authentication).isNotNull();
        assertThat(authentication.getPrincipal()).isEqualTo(USERNAME);
        assertThat(authentication.getCredentials()).isEqualTo(PASSWORD);
    }

    private BufferedReader getLoginRequest()
        throws JsonProcessingException, UnsupportedEncodingException {
        LoginRequest loginRequest = new LoginRequest(USERNAME, PASSWORD);
        String json = mapper.writeValueAsString(loginRequest);
        InputStreamReader inputStreamReader = new InputStreamReader(
            IOUtils.toInputStream(json, Charset.defaultCharset()));
        return new BufferedReader(inputStreamReader);
    }

}