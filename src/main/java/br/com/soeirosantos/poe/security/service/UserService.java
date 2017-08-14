package br.com.soeirosantos.poe.security.service;

import br.com.soeirosantos.poe.security.domain.entity.User;
import java.util.Optional;

public interface UserService {

    Optional<User> getByUsername(String username);

}
