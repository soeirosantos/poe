package br.com.soeirosantos.poe.core.services.impl;

import br.com.soeirosantos.poe.security.service.UserContextService;
import org.springframework.data.domain.AuditorAware;


public class AuditorAwareImpl implements AuditorAware<String> {

    private final UserContextService contextService;

    public AuditorAwareImpl(UserContextService context) {
        this.contextService = context;
    }

    @Override
    public String getCurrentAuditor() {
        return contextService.getUsername();
    }

}
