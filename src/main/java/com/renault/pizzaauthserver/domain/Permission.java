package com.renault.pizzaauthserver.domain;

/**
Format: "condition:permission"
 */

public enum Permission {
    READ("any:read"),
    WRITE("any:write"),
    UPDATE("any:update"),
    DELETE("any:delete"),
    READ_OWNER("owner:read"),
    WRITE_OWNER("owner:write"),
    UPDATE_OWNER("owner:update");

    public final String permission;

    Permission(String permission) {
        this.permission = permission;
    }
}
