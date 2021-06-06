package com.project.diploma.data.repositories;

import com.project.diploma.data.models.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AchievementRepository extends JpaRepository<Achievement, Long> {

    boolean existsByName(String name);

    Achievement getAchievementByName(String name);
}
