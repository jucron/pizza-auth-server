package com.renault.pizzaauthserver.domain;

import java.util.HashSet;
import java.util.Set;

import static com.renault.pizzaauthserver.domain.Permission.*;

public enum RoleList {
        USER(new HashSet<String>() {{
            add(READ_OWNER.permission); add(UPDATE_OWNER.permission); add(WRITE_OWNER.permission);
        }}),
        MANAGER(new HashSet<String>() {{
            add(READ.permission); add(UPDATE.permission); add(WRITE.permission);
        }}),
        ADMIN(new HashSet<String>() {{
            add(READ.permission); add(UPDATE.permission); add(WRITE.permission); add(DELETE.permission);
        }});
        private final Set<String> permissions;

        RoleList (Set<String> permissions) {
            this.permissions = permissions;
        }

        public Set<String> getPermissions () {
            return this.permissions;
        }
}
