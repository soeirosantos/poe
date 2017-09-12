package br.com.soeirosantos.poe.security.service.impl;

import br.com.soeirosantos.poe.core.config.ApplicationProperties;
import br.com.soeirosantos.poe.security.auth.jwt.extractor.TokenExtractor;
import br.com.soeirosantos.poe.security.domain.entity.User;
import br.com.soeirosantos.poe.security.exception.InvalidJwtToken;
import br.com.soeirosantos.poe.security.model.UserContext;
import br.com.soeirosantos.poe.security.model.token.JwtToken;
import br.com.soeirosantos.poe.security.model.token.JwtTokenFactory;
import br.com.soeirosantos.poe.security.model.token.RawAccessJwtToken;
import br.com.soeirosantos.poe.security.model.token.RefreshToken;
import br.com.soeirosantos.poe.security.service.RefreshJwtService;
import br.com.soeirosantos.poe.security.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
class RefreshJwtServiceImpl implements RefreshJwtService {

    private final TokenExtractor tokenExtractor;
    private final UserService userService;
    private final JwtTokenFactory tokenFactory;
    private final ApplicationProperties applicationProperties;

    RefreshJwtServiceImpl(
        TokenExtractor tokenExtractor,
        UserService userService,
        JwtTokenFactory tokenFactory,
        ApplicationProperties applicationProperties) {
        this.tokenExtractor = tokenExtractor;
        this.userService = userService;
        this.tokenFactory = tokenFactory;
        this.applicationProperties = applicationProperties;
    }

    @Override
    public JwtToken refresh(String jwtTokenHeader) {
        String token =
            tokenExtractor.extract(jwtTokenHeader);

        RawAccessJwtToken rawToken = new RawAccessJwtToken(token);
        RefreshToken refreshToken =
            RefreshToken.create(rawToken, applicationProperties.getSecurity().getTokenSigningKey())
                .orElseThrow(() -> new InvalidJwtToken());

        String subject = refreshToken.getSubject();
        User user = userService.getByUsername(subject)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + subject));

        if (!user.hasRoles()) {
            throw new InsufficientAuthenticationException("User has no roles assigned");
        }

        List<GrantedAuthority> authorities = user.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority(role.authority()))
            .collect(Collectors.toList());

        UserContext userContext = UserContext.create(user.getUsername(), authorities);

        return tokenFactory.createAccessJwtToken(userContext);
    }

}
