package com.project.diploma.data.repositories;

import com.project.diploma.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);

    User findUserByUsername(String name);

    boolean existsByEmail(String name);

    boolean existsByUsernameAndPassword(String username, String password);
}
