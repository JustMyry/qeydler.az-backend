package com.exnotis.backend.achievement.dto.request;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class epAddAchievement {

    private String achievement;
    private Integer stars; //10 uzerinden cetinlik derecesi (or prestige)
    private String context;
    private boolean isActive;



}
