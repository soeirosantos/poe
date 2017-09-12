package br.com.soeirosantos.poe.security.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import br.com.soeirosantos.poe.security.auth.jwt.extractor.TokenExtractor;
import br.com.soeirosantos.poe.security.domain.entity.User;
import br.com.soeirosantos.poe.security.model.token.JwtToken;
import br.com.soeirosantos.poe.security.service.RefreshJwtService;
import br.com.soeirosantos.poe.security.service.UserService;
import br.com.soeirosantos.poe.util.TokenGenerator;
import br.com.soeirosantos.poe.util.TokenGenerator.TokenType;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RefreshJwtServiceImplTest {

    @Autowired
    private RefreshJwtService refreshJwtService;

    @Autowired
    private TokenGenerator tokenGenerator;

    @MockBean
    private UserService userService;

    @Before
    public void setup() {
        given(userService.getByUsername(TokenGenerator.MOCK_USER_FOR_TOKEN.getUsername()))
            .willReturn(getUser());
    }

    @Test
    public void refresh() throws Exception {
        JwtToken token = refreshJwtService
            .refresh(TokenExtractor.HEADER_PREFIX + tokenGenerator.generateToken(TokenType.REFRESH));
        assertThat(token.getToken()).isNotNull().isNotEmpty();
    }

    private Optional<User> getUser() {
        return Optional.of(TokenGenerator.MOCK_USER_FOR_TOKEN);
    }

}