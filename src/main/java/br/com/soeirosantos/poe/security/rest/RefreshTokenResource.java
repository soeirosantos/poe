package br.com.soeirosantos.poe.security.rest;


import br.com.soeirosantos.poe.security.config.WebSecurityConfig;
import br.com.soeirosantos.poe.security.exception.JwtExpiredTokenException;
import br.com.soeirosantos.poe.security.model.token.JwtToken;
import br.com.soeirosantos.poe.security.rest.errors.ErrorCode;
import br.com.soeirosantos.poe.security.rest.errors.ErrorResponse;
import br.com.soeirosantos.poe.security.service.RefreshJwtService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = RefreshTokenResource.PATH)
class RefreshTokenResource {

    public static final String PATH = "/api/auth/token";

    private final RefreshJwtService refreshJwtService;

    RefreshTokenResource(
        RefreshJwtService refreshJwtService) {
        this.refreshJwtService = refreshJwtService;
    }

    @GetMapping
    JwtToken refreshToken(HttpServletRequest request) {
        return refreshJwtService.refresh(request.getHeader(WebSecurityConfig.JWT_TOKEN_HEADER));
    }

    @ExceptionHandler(JwtExpiredTokenException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    ErrorResponse handleJwtExpiredTokenException(JwtExpiredTokenException e) {
        return ErrorResponse
            .of(HttpStatus.UNAUTHORIZED, ErrorCode.JWT_TOKEN_EXPIRED.getMessage(),
                ErrorCode.JWT_TOKEN_EXPIRED);
    }
}
