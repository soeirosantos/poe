package br.com.soeirosantos.poe.security.service.impl;

import br.com.soeirosantos.poe.security.domain.entity.User;
import br.com.soeirosantos.poe.security.domain.repository.UserRepository;
import br.com.soeirosantos.poe.security.service.UserService;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
