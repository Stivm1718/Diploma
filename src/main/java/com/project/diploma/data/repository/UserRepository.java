package com.project.diploma.data.repository;

import com.project.diploma.data.models.User;
import com.project.diploma.service.models.LoginServiceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);

    LoginServiceModel findByUsernameAndPassword(String username, String password);

    User findByUsername(String name);

    boolean existsByEmail(String name);

    boolean existsByUsernameAndPassword(String username, String password);
}
