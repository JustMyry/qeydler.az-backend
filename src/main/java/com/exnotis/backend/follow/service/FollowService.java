package com.exnotis.backend.follow.service;

import com.exnotis.backend.epcommunication.request.epDoubleData;
import com.exnotis.backend.epcommunication.request.epJustJwt;
import com.exnotis.backend.epcommunication.request.epSingleData;
import com.exnotis.backend.epcommunication.response.epResponseModel;
import com.exnotis.backend.follow.dto.response.FollowUser;

import java.util.List;

public interface FollowService {
    epResponseModel<String> followUser(epJustJwt data, String followingId);

    epResponseModel<String> unfollowUser(epJustJwt data, String followingId);

    epResponseModel<String> userAcceptedFollowRequest(epSingleData data);

    epResponseModel<String> deleteFollowRequest(epSingleData data);

    epResponseModel<String> userDeniedFollowRequest(epSingleData data);

    epResponseModel<List<FollowUser>> getUserFollowers(epDoubleData data, String followingId);
}
