package com.renault.pizzaauthserver.domain;

public class NoPermissionException extends RuntimeException{
    public NoPermissionException() {
    }

    public NoPermissionException(String message) {
        super(message);
    }
}
