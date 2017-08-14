package br.com.soeirosantos.poe.security.model.token;

import br.com.soeirosantos.poe.core.config.ApplicationProperties;
import br.com.soeirosantos.poe.security.model.Scopes;
import br.com.soeirosantos.poe.security.model.UserContext;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenFactory {

    private final ApplicationProperties properties;

    JwtTokenFactory(ApplicationProperties properties) {
        this.properties = properties;
    }

    public AccessJwtToken createAccessJwtToken(UserContext userContext) {
      if (StringUtils.isBlank(userContext.getUsername())) {
        throw new IllegalArgumentException("Cannot create JWT Token without username");
      }

      if (userContext.getAuthorities() == null || userContext.getAuthorities().isEmpty()) {
        throw new IllegalArgumentException("User doesn't have any privileges");
      }

        Claims claims = Jwts.claims().setSubject(userContext.getUsername());
        claims.put("scopes",
            userContext.getAuthorities().stream().map(s -> s.toString())
                .collect(Collectors.toList()));

        LocalDateTime currentTime = LocalDateTime.now();

        String token =
            Jwts.builder().setClaims(claims).setIssuer(properties.getSecurity().getTokenIssuer())
                .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(
                    Date.from(
                        currentTime.plusMinutes(properties.getSecurity().getTokenExpirationTime())
                            .atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS512, properties.getSecurity().getTokenSigningKey())
                .compact();

        return new AccessJwtToken(token, claims);
    }

    public JwtToken createRefreshToken(UserContext userContext) {
        if (StringUtils.isBlank(userContext.getUsername())) {
            throw new IllegalArgumentException("Cannot create JWT Token without username");
        }

        LocalDateTime currentTime = LocalDateTime.now();

        Claims claims = Jwts.claims().setSubject(userContext.getUsername());
        claims.put("scopes", Arrays.asList(Scopes.REFRESH_TOKEN.authority()));

        String token = Jwts.builder().setClaims(claims)
            .setIssuer(properties.getSecurity().getTokenIssuer())
            .setId(UUID.randomUUID().toString())
            .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
            .setExpiration(
                Date.from(currentTime.plusMinutes(properties.getSecurity().getRefreshTokenExpTime())
                    .atZone(ZoneId.systemDefault()).toInstant()))
            .signWith(SignatureAlgorithm.HS512, properties.getSecurity().getTokenSigningKey())
            .compact();

        return new AccessJwtToken(token, claims);
    }
}
