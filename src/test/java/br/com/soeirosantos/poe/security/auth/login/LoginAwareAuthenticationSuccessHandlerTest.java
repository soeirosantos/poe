package br.com.soeirosantos.poe.security.auth.login;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.soeirosantos.poe.security.domain.entity.Role;
import br.com.soeirosantos.poe.security.model.UserContext;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collections;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginAwareAuthenticationSuccessHandlerTest {

    @Autowired
    private LoginAwareAuthenticationSuccessHandler successHandler;

    @Test
    public void onAuthenticationSuccess() throws Exception {
        Authentication authentication = new UsernamePasswordAuthenticationToken(getUserContext(),
            null);
        HttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        successHandler.onAuthenticationSuccess(request, response, authentication);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_VALUE);
        assertThat(response.getContentAsString()).contains("token").contains("refreshToken");
    }


    private UserContext getUserContext() {
        GrantedAuthority authority = new SimpleGrantedAuthority(Role.ADMIN.authority());
        return UserContext.create("username", Collections.singletonList(authority));
    }

}