package br.com.soeirosantos.poe.security.auth;

import br.com.soeirosantos.poe.security.model.UserContext;
import br.com.soeirosantos.poe.security.model.token.RawAccessJwtToken;
import java.util.Collection;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private RawAccessJwtToken rawAccessToken;
    private UserContext userContext;

    public JwtAuthenticationToken(RawAccessJwtToken rawAccessJwtToken) {
        super(null);
        this.rawAccessToken = rawAccessJwtToken;
        this.setAuthenticated(false);
    }

    public JwtAuthenticationToken(UserContext userContext,
        Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.eraseCredentials();
        this.userContext = userContext;
        super.setAuthenticated(true);
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        if (authenticated) {
            throw new IllegalArgumentException(
                "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }
        super.setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return rawAccessToken;
    }

    @Override
    public Object getPrincipal() {
        return this.userContext;
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.rawAccessToken = null;
    }

}
