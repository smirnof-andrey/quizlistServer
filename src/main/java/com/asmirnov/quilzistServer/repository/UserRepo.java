package com.asmirnov.quilzistServer.repository;

import com.asmirnov.quilzistServer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findById(long id);
    User findByUsernameAndPassword(String username, String password);
}
