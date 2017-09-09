package br.com.soeirosantos.poe.security.auth.login;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.soeirosantos.poe.security.exception.AuthMethodNotSupportedException;
import br.com.soeirosantos.poe.security.exception.JwtExpiredTokenException;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginAwareAuthenticationFailureHandlerTest {

    @Autowired
    private LoginAwareAuthenticationFailureHandler failureHandler;

    @Test
    public void onAuthenticationFailure() throws Exception {

        List<AuthenticationException> exceptions = Arrays.asList(
            new BadCredentialsException("bar"),
            new JwtExpiredTokenException(null, null, null),
            new AuthMethodNotSupportedException("foo"));

        for (AuthenticationException exception : exceptions) {
            HttpServletRequest request = new MockHttpServletRequest();
            MockHttpServletResponse response = new MockHttpServletResponse();
            failureHandler.onAuthenticationFailure(request, response, exception);
            assertThat(response.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
            assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_VALUE);
        }
    }

}