package com.project.diploma.data.repository;

import com.project.diploma.data.models.Hero;
import com.project.diploma.data.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HeroRepository extends JpaRepository<Hero, Long> {

    Hero findHeroByName(String name);

    @Override
    List<Hero> findAll();
}
