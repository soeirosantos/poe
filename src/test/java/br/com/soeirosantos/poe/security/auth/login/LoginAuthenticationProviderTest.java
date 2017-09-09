package br.com.soeirosantos.poe.security.auth.login;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import br.com.soeirosantos.poe.security.domain.entity.Role;
import br.com.soeirosantos.poe.security.domain.entity.User;
import br.com.soeirosantos.poe.security.service.UserService;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginAuthenticationProviderTest {

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    @Autowired
    private LoginAuthenticationProvider authenticationProvider;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @MockBean
    private UserService userService;

    @Test
    public void authenticate() throws Exception {
        given(userService.getByUsername(USERNAME)).willReturn(getUser());
        Authentication auth = new UsernamePasswordAuthenticationToken(USERNAME, PASSWORD);
        Authentication authenticated = authenticationProvider.authenticate(auth);
        assertThat(authenticated.getPrincipal()).isNotNull();
        assertThat(authenticated.getAuthorities()).size().isEqualTo(1);
    }

    private Optional<User> getUser() {
        User user = new User(USERNAME, encoder.encode(PASSWORD));
        user.addRole(Role.ADMIN);
        return Optional.of(user);
    }

}