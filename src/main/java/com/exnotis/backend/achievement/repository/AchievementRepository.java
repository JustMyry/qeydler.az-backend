package com.exnotis.backend.achievement.repository;

import com.exnotis.backend.achievement.model.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AchievementRepository extends JpaRepository<Achievement, Long> {

    Achievement findAchievementByAchievement(String achievement);

    Achievement findAchievementById(String id);

}
