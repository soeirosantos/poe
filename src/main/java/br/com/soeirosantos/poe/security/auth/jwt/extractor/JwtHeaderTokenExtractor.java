package br.com.soeirosantos.poe.security.auth.jwt.extractor;

import java.util.Objects;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;

@Component
class JwtHeaderTokenExtractor implements TokenExtractor {

    @Override
    public String extract(String header) {
        Objects.requireNonNull(header);
        if (!header.contains(HEADER_PREFIX)) {
            throw new AuthenticationServiceException(
                "Invalid authorization header prefix. Use " + HEADER_PREFIX);
        }
        return header.substring(HEADER_PREFIX.length(), header.length());
    }

}
