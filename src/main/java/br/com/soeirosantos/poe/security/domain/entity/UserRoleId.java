package br.com.soeirosantos.poe.security.domain.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Embeddable
class UserRoleId implements Serializable {

    @Setter
    @Column(name = "USER_ID")
    private Long userId;

    @NonNull
    @Enumerated(EnumType.STRING)
    private Role role;

}
