package br.com.soeirosantos.poe.security.domain.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.Getter;

@Getter
@Entity
public class UserRole {

    @Embeddable
    public static class Id implements Serializable {

        @Column(name = "USER_ID")
        private Long userId;

        @Enumerated(EnumType.STRING)
        private Role role;

    }

    @EmbeddedId
    private Id id = new Id();

    @Enumerated(EnumType.STRING)
    @Column(insertable = false, updatable = false)
    private Role role;

}
