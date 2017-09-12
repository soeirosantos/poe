package br.com.soeirosantos.poe.security.auth.jwt;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.soeirosantos.poe.security.auth.JwtAuthenticationToken;
import br.com.soeirosantos.poe.security.model.token.RawAccessJwtToken;
import br.com.soeirosantos.poe.util.TokenGenerator;
import br.com.soeirosantos.poe.util.TokenGenerator.TokenType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JwtAuthenticationProviderTest {

    @Autowired
    private JwtAuthenticationProvider authenticationProvider;

    @Autowired
    private TokenGenerator tokenGenerator;

    @Test
    public void authenticate() throws Exception {
        RawAccessJwtToken rawToken = new RawAccessJwtToken(
            tokenGenerator.generateToken(TokenType.ACCESS));
        Authentication authentication = new JwtAuthenticationToken(rawToken);
        Authentication authenticated = authenticationProvider.authenticate(authentication);
        assertThat(authenticated.getPrincipal()).isNotNull();
        assertThat(authenticated.getAuthorities()).isNotEmpty();
    }

}