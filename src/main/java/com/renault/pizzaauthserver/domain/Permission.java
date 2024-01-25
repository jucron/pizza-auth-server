package com.renault.pizzaauthserver.domain;


public enum Permission {
    READ("any:read"),
    WRITE("any:write"),
    UPDATE("any:update"),
    DELETE("any:delete");

    public final String permission;

    Permission(String permission) {
        this.permission = permission;
    }
}
