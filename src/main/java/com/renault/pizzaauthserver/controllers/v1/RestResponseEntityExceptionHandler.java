package com.renault.pizzaauthserver.controllers.v1;
//

import com.renault.pizzaauthserver.domain.UsernameTakenException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({UsernameTakenException.class})
    public ResponseEntity<Object> handleUsernameTakenException(Exception exception, WebRequest request) {
        log.info("INFO: handleUsernameTakenException called: Username is already taken");
        return new ResponseEntity<Object>("Username already taken", new HttpHeaders(), HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler({SessionAuthenticationException.class})
    public ResponseEntity<Object> handleSessionAuthenticationException(Exception exception, WebRequest request) {
        log.info("INFO: handleSessionAuthenticationException called: Authentication failed. " +
                "\nmessage: "+exception.getMessage()+
                "\ncause: "+exception.getCause());
        return new ResponseEntity<Object>("Authentication failed", new HttpHeaders(), HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<Object> handleAuthenticationException(Exception exception, WebRequest request) {
        log.info("INFO: handleAuthenticationException called: Authentication failed. " +
                "\nmessage: "+exception.getMessage()+
                "\ncause: "+exception.getCause());
        return new ResponseEntity<Object>("Authentication failed", new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({ExpiredJwtException.class})
    public ResponseEntity<Object> handleExpiredJwtException(Exception exception, WebRequest request) {
        log.info("INFO: handleExpiredJwtException called");
        return new ResponseEntity<Object>("JWT expired", new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({JwtException.class})
    public ResponseEntity<Object> handleJwtException(Exception exception, WebRequest request) {
        log.info("INFO: handleJwtException called");
        return new ResponseEntity<Object>("JWT not accepted", new HttpHeaders(), HttpStatus.FORBIDDEN);
    }
}
//
