package br.com.soeirosantos.poe.core.web.error;

import br.com.soeirosantos.poe.content.exception.EncryptContentException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
class ExceptionTranslator {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse processValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        String description = "One or more validation errors were found";
        ErrorResponse error = new ErrorResponse(ErrorConstants.ERR_VALIDATION, description);
        for (FieldError fieldError : fieldErrors) {
            error.add(fieldError.getObjectName(), fieldError.getField(),
                fieldError.getDefaultMessage());
        }
        return error;
    }

    @ExceptionHandler(EncryptContentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse processEncryptContentException(
        EncryptContentException exception) {
        return new ErrorResponse(ErrorConstants.ERR_CONTENT_TOKEN_NOT_PROVIDED, exception.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ErrorResponse processMethodNotSupportedException(
        HttpRequestMethodNotSupportedException exception) {
        return new ErrorResponse(ErrorConstants.ERR_METHOD_NOT_SUPPORTED, exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> processException(Exception ex) {
        log.error(ex.getMessage(), ex);
        BodyBuilder builder;
        ErrorResponse error;
        ResponseStatus responseStatus =
            AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class);
        if (responseStatus != null) {
            builder = ResponseEntity.status(responseStatus.value());
            error = new ErrorResponse("error." + responseStatus.value().value(),
                responseStatus.reason());
        } else {
            builder = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
            error = new ErrorResponse(ErrorConstants.ERR_INTERNAL_SERVER_ERROR,
                "Internal server error");
        }
        return builder.body(error);
    }
}
