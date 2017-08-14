package br.com.soeirosantos.poe.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Not found")
public class EntityNotFound extends RuntimeException {

    private static final long serialVersionUID = 1L;

}
