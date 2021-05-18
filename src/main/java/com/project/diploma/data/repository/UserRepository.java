package com.project.diploma.data.repository;

import com.project.diploma.data.models.Hero;
import com.project.diploma.data.models.User;
import com.project.diploma.service.models.LoginUserServiceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);

    LoginUserServiceModel findByUsernameAndPassword(String username, String password);

    User findUserByUsername(String name);

    boolean existsByEmail(String name);

    boolean existsByUsernameAndPassword(String username, String password);
}
