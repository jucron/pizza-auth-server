package com.renault.pizzaauthserver.domain;

public class UsernameTakenException extends RuntimeException{
    public UsernameTakenException() {
    }

    public UsernameTakenException(String message) {
        super(message);
    }
}
