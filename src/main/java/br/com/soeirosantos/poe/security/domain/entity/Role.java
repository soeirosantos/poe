package br.com.soeirosantos.poe.security.domain.entity;

public enum Role {

    ADMIN,
    PREMIUM_MEMBER,
    MEMBER;

    public String authority() {
        return "ROLE_" + this.name();
    }

}
