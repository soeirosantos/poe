package br.com.soeirosantos.poe.security.auth.login.extractor;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

public interface AuthenticationExtractor {

    Authentication extract(HttpServletRequest request) throws IOException;
}
