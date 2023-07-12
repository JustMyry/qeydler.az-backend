package com.exnotis.backend.achievement.model;

import com.exnotis.backend.epmodel.BaseModel;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Achievement extends BaseModel {

    //@Column(unique = true)
    private String achievement;
    private Integer stars; //10 uzerinden cetinlik derecesi (or prestige)
    private String context;
    private boolean isActive;


    public String getAchievement() { return achievement; }

    public void setAchievement(String achievement) { this.achievement = achievement; }

    public Integer getStars() { return stars; }

    public void setStars(Integer stars) { this.stars = stars; }

    public String getContext() { return context; }

    public void setContext(String context) { this.context = context; }

    public boolean isActive() { return isActive; }

    public void setActive(boolean active) { isActive = active; }
}