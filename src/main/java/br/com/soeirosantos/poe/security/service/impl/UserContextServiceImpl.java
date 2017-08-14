package br.com.soeirosantos.poe.security.service.impl;

import br.com.soeirosantos.poe.security.auth.JwtAuthenticationToken;
import br.com.soeirosantos.poe.security.model.UserContext;
import br.com.soeirosantos.poe.security.service.UserContextService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
class UserContextServiceImpl implements UserContextService {

    @Override
    public String getUsername() {
        return getCurrentUserLogin();
    }

    private String getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String username = null;
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof UserDetails) {
                UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
                username = springSecurityUser.getUsername();
            } else if (authentication.getPrincipal() instanceof UserContext) {
                UserContext springSecurityUser = (UserContext) authentication.getPrincipal();
                username = springSecurityUser.getUsername();
            } else if (authentication.getPrincipal() instanceof String) {
                username = (String) authentication.getPrincipal();
            }
        }
        return username;
    }

}
