package com.renault.pizzaauthserver.repositories;

import com.renault.pizzaauthserver.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
