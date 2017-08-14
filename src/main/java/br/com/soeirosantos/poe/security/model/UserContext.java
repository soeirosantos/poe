package br.com.soeirosantos.poe.security.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.security.core.GrantedAuthority;

@Getter
@AllArgsConstructor(staticName = "create")
public class UserContext {

    @NonNull
    private final String username;
    private final List<GrantedAuthority> authorities;

}
