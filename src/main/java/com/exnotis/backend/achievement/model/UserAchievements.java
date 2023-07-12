package com.exnotis.backend.achievement.model;


import com.exnotis.backend.epmodel.BaseModel;
import jakarta.persistence.Entity;
import lombok.NonNull;

@Entity
public class UserAchievements extends BaseModel {


    @NonNull
    private String ownerUserId;

    private Long achievementId;


    //Getter and Setters


    public String getOwnerUserId() {
        return ownerUserId;
    }

    public void setOwnerUserId(String ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    public Long getAchievementId() {
        return achievementId;
    }

    public void setAchievementId(Long achievementId) {
        this.achievementId = achievementId;
    }
}
