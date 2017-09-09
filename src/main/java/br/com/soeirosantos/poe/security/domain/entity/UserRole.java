package br.com.soeirosantos.poe.security.domain.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(staticName = "of")
@NoArgsConstructor
@Entity
public class UserRole {

    @NonNull
    @EmbeddedId
    private UserRoleId id;

    public Role getRole() {
        return id.getRole();
    }
}
