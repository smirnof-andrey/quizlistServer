package com.asmirnov.quilzistServer.repository;

import com.asmirnov.quilzistServer.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findById(long id);
}
