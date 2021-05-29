package com.project.diploma.data.repositories;

import com.project.diploma.data.models.Hero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeroRepository extends JpaRepository<Hero, Long> {

    Hero findHeroByName(String name);

    boolean existsHeroByName(String name);
}
