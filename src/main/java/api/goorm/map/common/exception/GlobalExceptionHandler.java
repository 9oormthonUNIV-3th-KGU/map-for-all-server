package api.goorm.map.common.exception;

import api.goorm.map.common.oauth2.exception.CustomJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomJwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleCustomJwtException(CustomJwtException e) {
        return e.getMessage();
    }
}
