package com.exnotis.backend.achievement.service.impl;


import com.exnotis.backend.achievement.dto.request.epAddAchievement;
import com.exnotis.backend.achievement.model.Achievement;
import com.exnotis.backend.achievement.model.UserAchievements;
import com.exnotis.backend.achievement.repository.AchievementRepository;
import com.exnotis.backend.achievement.repository.UserAchievementsRepository;
import com.exnotis.backend.achievement.service.AchievementService;
import com.exnotis.backend.epcommunication.response.epResponseModel;
import com.exnotis.backend.epcommunication.response.epResponseStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AchievementServiceImpl implements AchievementService {

    private final AchievementRepository repository;
    private final UserAchievementsRepository userAchievementsRepository;
    public AchievementServiceImpl(AchievementRepository repository, UserAchievementsRepository userAchievementsRepository){
        this.repository = repository;
        this.userAchievementsRepository = userAchievementsRepository;
    }

    public epResponseModel<Achievement> addAchievement(epAddAchievement data){
        if(repository.findAchievementByAchievement(data.getAchievement())!=null){
            return epResponseModel.<Achievement>builder()
                    .response(null)
                    .status(epResponseStatus.builder().message("Achievement is already exists in exnotis DB.").code(101).build())
                    .build();
        }
        Achievement achievement = Achievement.builder()
                .achievement(data.getAchievement())
                .context(data.getContext())
                .stars(data.getStars())
                .isActive(true)
                .build();
        repository.save(achievement);
        return epResponseModel.<Achievement>builder()
                .response(achievement)
                .status(epResponseStatus.getSuccess())
                .build();
    }


    public epResponseModel<String> deleteAchievement(String id) {
        repository.delete(repository.findAchievementById(id));
        return epResponseModel.<String>builder()
                .response(null)
                .status(epResponseStatus.getSuccess())
                .build();
    }

    public epResponseModel<Achievement> editAchievement(String id, epAddAchievement data) {
        Achievement achievement = repository.findAchievementById(id);
        if(achievement==null){
            return epResponseModel.<Achievement>builder()
                    .response(null)
                    .status(epResponseStatus.builder().message("Achievement is not exists in exnotis DB").code(404).build())
                    .build();
        }
        achievement.setAchievement(data.getAchievement());
        achievement.setContext(data.getContext());
        achievement.setStars(data.getStars());
        achievement.setActive(data.isActive());
        repository.save(achievement);
        return epResponseModel.<Achievement>builder()
                .response(achievement)
                .status(epResponseStatus.getSuccess())
                .build();
    }

    @Override
    public List<UserAchievements> getUsersAchievements(String userId) {
        return userAchievementsRepository.findAllByOwnerUserId(userId);
    }


}
