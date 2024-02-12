package com.renault.pizzaauthserver.controllers.v1;
//

import com.renault.pizzaauthserver.domain.UsernameTakenException;
import com.renault.pizzaauthserver.domain.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(Exception exception, WebRequest request) {

        return new ResponseEntity<Object>("Resource Not Found", new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({UsernameTakenException.class})
    public ResponseEntity<Object> handleUsernameTakenException(Exception exception, WebRequest request) {
        log.info("INFO: handleUsernameTakenException called: Username is already taken");
        return new ResponseEntity<Object>("Username already taken", new HttpHeaders(), HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<Object> handleAuthenticationException(Exception exception, WebRequest request) {
        log.info("INFO: handleAuthenticationException called: Authentication failed. " +
                "\nmessage: "+exception.getMessage()+
                "\ncause: "+exception.getCause());
        return new ResponseEntity<Object>("Authentication failed", new HttpHeaders(), HttpStatus.FORBIDDEN);
    }
}
//
