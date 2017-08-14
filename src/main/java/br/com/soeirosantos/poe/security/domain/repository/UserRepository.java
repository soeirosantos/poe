package br.com.soeirosantos.poe.security.domain.repository;

import br.com.soeirosantos.poe.security.domain.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u left join fetch u.roles r where u.username = :username")
    Optional<User> findByUsername(@Param("username") String username);

}
