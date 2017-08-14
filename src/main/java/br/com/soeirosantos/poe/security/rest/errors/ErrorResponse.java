package br.com.soeirosantos.poe.security.rest.errors;

import java.util.Date;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor(staticName = "of")
@Getter
public class ErrorResponse {

    private final HttpStatus status;
    private final String message;
    private final ErrorCode errorCode;
    private final Date timestamp = new Date();

}
