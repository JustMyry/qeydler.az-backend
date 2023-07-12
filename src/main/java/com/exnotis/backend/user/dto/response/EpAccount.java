package com.exnotis.backend.user.dto.response;

import com.exnotis.backend.achievement.model.Achievement;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;

@Builder
@Setter
@Getter
public class EpAccount {

    @NonNull
    private String userId;

    private String username;
    private String email;
    private String userProfilePicture;
    private String userBio;
    private List<Achievement> userAchievements;
    private Integer followers;
    private Integer followings;
    private String privacy;

}
