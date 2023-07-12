package com.exnotis.backend.follow.model;

import com.exnotis.backend.epmodel.BaseModel;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;


@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class FollowTable extends BaseModel{

    private String follower;

    private String following;

    private boolean isActive;



    //Getters and Setters
    public String getFollower() {
        return follower;
    }

    public void setFollower(String follower) {
        this.follower = follower;
    }

    public String getFollowing() {
        return following;
    }

    public void setFollowing(String following) {
        this.following = following;
    }

    public boolean isActive() { return isActive; }

    public void setActive(boolean active) { isActive = active; }
}
