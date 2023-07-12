package com.exnotis.backend.achievement.service;

import com.exnotis.backend.achievement.dto.request.epAddAchievement;
import com.exnotis.backend.achievement.model.Achievement;
import com.exnotis.backend.achievement.model.UserAchievements;
import com.exnotis.backend.epcommunication.response.epResponseModel;

import java.util.List;

public interface AchievementService {

    epResponseModel<Achievement> addAchievement(epAddAchievement data);

    epResponseModel<String> deleteAchievement(String id);

    epResponseModel<Achievement> editAchievement(String id, epAddAchievement data);

    List<UserAchievements> getUsersAchievements(String userId);

}
