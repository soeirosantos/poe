package br.com.soeirosantos.poe.security.rest;


import br.com.soeirosantos.poe.core.config.ApplicationProperties;
import br.com.soeirosantos.poe.security.auth.jwt.extractor.TokenExtractor;
import br.com.soeirosantos.poe.security.config.WebSecurityConfig;
import br.com.soeirosantos.poe.security.domain.entity.User;
import br.com.soeirosantos.poe.security.exception.InvalidJwtToken;
import br.com.soeirosantos.poe.security.model.UserContext;
import br.com.soeirosantos.poe.security.model.token.JwtToken;
import br.com.soeirosantos.poe.security.model.token.JwtTokenFactory;
import br.com.soeirosantos.poe.security.model.token.RawAccessJwtToken;
import br.com.soeirosantos.poe.security.model.token.RefreshToken;
import br.com.soeirosantos.poe.security.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class RefreshTokenResource {

    @Autowired
    private JwtTokenFactory tokenFactory;

    @Autowired
    private ApplicationProperties applicationProperties;

    @Autowired
    private UserService userService;

    @Autowired
    @Qualifier("jwtHeaderTokenExtractor")
    private TokenExtractor tokenExtractor;

    @GetMapping(value = "/api/auth/token", produces = {MediaType.APPLICATION_JSON_VALUE})
    JwtToken refreshToken(HttpServletRequest request,
        HttpServletResponse response) {
        String tokenPayload =
            tokenExtractor.extract(request.getHeader(WebSecurityConfig.JWT_TOKEN_HEADER));

        RawAccessJwtToken rawToken = new RawAccessJwtToken(tokenPayload);
        RefreshToken refreshToken =
            RefreshToken.create(rawToken, applicationProperties.getSecurity().getTokenSigningKey())
                .orElseThrow(() -> new InvalidJwtToken());

        String subject = refreshToken.getSubject();
        User user = userService.getByUsername(subject)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + subject));

      if (user.getRoles() == null) {
        throw new InsufficientAuthenticationException("User has no roles assigned");
      }
        List<GrantedAuthority> authorities = user.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority(role.authority()))
            .collect(Collectors.toList());

        UserContext userContext = UserContext.create(user.getUsername(), authorities);

        return tokenFactory.createAccessJwtToken(userContext);
    }
}
