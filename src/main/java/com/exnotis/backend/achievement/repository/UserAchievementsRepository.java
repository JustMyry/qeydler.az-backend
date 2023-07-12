package com.exnotis.backend.achievement.repository;

import com.exnotis.backend.achievement.model.UserAchievements;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserAchievementsRepository extends JpaRepository<UserAchievements, Long> {


    List<UserAchievements> findAllByOwnerUserId(String ownerUserId);

}
