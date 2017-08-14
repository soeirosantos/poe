package br.com.soeirosantos.poe.security.rest;

import br.com.soeirosantos.poe.security.auth.JwtAuthenticationToken;
import br.com.soeirosantos.poe.security.model.UserContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ProfileResource {

    @GetMapping(value = "/api/me")
    UserContext get(JwtAuthenticationToken token) {
        return (UserContext) token.getPrincipal();
    }

}
