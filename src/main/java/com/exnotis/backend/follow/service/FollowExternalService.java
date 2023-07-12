package com.exnotis.backend.follow.service;

public interface FollowExternalService {

    public Integer getUserFollowerCount(String userId);

    public Integer getUserFollowingCount(String userId);

}
