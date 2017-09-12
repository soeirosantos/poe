package br.com.soeirosantos.poe.security.rest.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    AUTHENTICATION("Invalid username or password"),
    BAD_CREDENTIALS("Invalid username or password"),
    JWT_TOKEN_EXPIRED("Token has expired");

    private String message;

}
