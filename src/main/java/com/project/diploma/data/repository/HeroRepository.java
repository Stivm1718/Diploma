package com.project.diploma.data.repository;

import com.project.diploma.data.models.Hero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HeroRepository extends JpaRepository<Hero, Long> {

    Optional<Hero> findByName(String name);
}
