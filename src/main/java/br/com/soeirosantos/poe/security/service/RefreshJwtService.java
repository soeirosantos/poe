package br.com.soeirosantos.poe.security.service;

import br.com.soeirosantos.poe.security.model.token.JwtToken;

public interface RefreshJwtService {

    JwtToken refresh(String jwtTokenHeader);
}
