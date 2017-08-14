package br.com.soeirosantos.poe.security.model.token;

import br.com.soeirosantos.poe.security.model.Scopes;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RefreshToken implements JwtToken {

    private Jws<Claims> claims;

    @SuppressWarnings("unchecked")
    public static Optional<RefreshToken> create(RawAccessJwtToken token, String signingKey) {
        Jws<Claims> claims = token.parseClaims(signingKey);

        List<String> scopes = claims.getBody().get("scopes", List.class);
        if (scopes == null || scopes.isEmpty() || !scopes.stream()
            .anyMatch(scope -> Scopes.REFRESH_TOKEN.authority().equals(scope))) {
            return Optional.empty();
        }

        return Optional.of(new RefreshToken(claims));
    }

    @Override
    public String getToken() {
        return null;
    }

    public String getSubject() {
        return claims.getBody().getSubject();
    }

}
