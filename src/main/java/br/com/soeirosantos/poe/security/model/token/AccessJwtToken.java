package br.com.soeirosantos.poe.security.model.token;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.jsonwebtoken.Claims;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public final class AccessJwtToken implements JwtToken {

    @Getter(value = AccessLevel.NONE)
    private final String rawToken;

    @JsonIgnore
    private Claims claims;

    @Override
    public String getToken() {
        return this.rawToken;
    }

}
