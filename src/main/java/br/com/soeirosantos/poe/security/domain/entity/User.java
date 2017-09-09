package br.com.soeirosantos.poe.security.domain.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(name = "username")
    private String username;

    @NonNull
    @Column(name = "password")
    private String password;

    //TODO: refactor this relationship and kill the UserRole class
    @OneToMany
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private List<UserRole> roles;

    public Set<Role> getRoles() {
        if (roles == null) {
            roles = new ArrayList<>();
        }
        return roles.stream().map(ur -> ur.getRole()).collect(Collectors.toSet());
    }

    public boolean hasRoles() {
        return roles != null && !roles.isEmpty();
    }

    public void addRole(Role role) {
        if (roles == null) {
            roles = new ArrayList<>();
        }
        UserRoleId id = new UserRoleId(role);
        id.setUserId(this.id);
        roles.add(UserRole.of(id));
    }
}
