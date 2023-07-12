package com.exnotis.backend.follow.service.impl;


import com.exnotis.backend.follow.repository.FollowRepository;
import com.exnotis.backend.follow.service.FollowExternalService;
import org.springframework.stereotype.Service;


@Service
public class FollowExternalServiceImpl implements FollowExternalService {

    private final FollowRepository repository;
    public FollowExternalServiceImpl(FollowRepository repository){
        this.repository = repository;
    }

    @Override
    public Integer getUserFollowerCount(String userId) {
        return repository.findFollowTablesByFollowing(userId).size();
    }

    @Override
    public Integer getUserFollowingCount(String userId) {
        return repository.findFollowTablesByFollower(userId).size();
    }
}
