package com.renault.pizzaauthserver.repositories;

import com.renault.pizzaauthserver.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Long> {
        Role findByName(String roleName);
}
