package com.exnotis.backend.follow.service.impl;


import com.exnotis.backend.epcommunication.request.epDoubleData;
import com.exnotis.backend.epcommunication.request.epJustJwt;
import com.exnotis.backend.epcommunication.request.epSingleData;
import com.exnotis.backend.epcommunication.response.epResponseModel;
import com.exnotis.backend.epcommunication.response.epResponseStatus;
import com.exnotis.backend.follow.dto.response.FollowUser;
import com.exnotis.backend.follow.model.FollowTable;
import com.exnotis.backend.follow.repository.FollowRepository;
import com.exnotis.backend.follow.service.FollowService;
import com.exnotis.backend.security.jwt.JwtProvider;
import com.exnotis.backend.user.model.AppUser;
import com.exnotis.backend.user.service.UserExternalService;
import org.springframework.stereotype.Service;

import java.util.List;


/***
 * @Author: Exnotis Corporation.
 * @Target: FollowTable ucun Service interface'i implenation'udur.
 * @Comment: Hec bir serh yoxdur.
 * */

@Service
public class FollowServiceImpl implements FollowService {

    private final FollowRepository repository;
    private final UserExternalService userService;
    private final JwtProvider jwtProvider;
    public FollowServiceImpl(FollowRepository repository, JwtProvider jwtProvider, UserExternalService userService){
        this.repository = repository;
        this.userService = userService;
        this.jwtProvider = jwtProvider;

    }



    @Override
    public epResponseModel<String> followUser(epJustJwt data, String followingUsername) {
        AppUser followerUser = userService.findAppUserByUserId(jwtProvider.getSubject(data.getJwt()));
        AppUser followingUser = userService.findUserByUsername(followingUsername);
        if(followerUser == null || followingUser == null || userService.isUserBlocked(followingUser.getUserId(), followerUser.getUserId()) || !followerUser.isNotLocked())
            return epResponseModel.<String>builder()
                    .response("Access Denied")
                    .status(epResponseStatus.getRequestIsInvalid())
                    .build();
        FollowTable test = repository.findFollowTableByFollowerAndFollowing(followerUser.getUserId(), followingUser.getUserId());
        if(test != null)
            return epResponseModel.<String>builder()
                .response("Already Requested")
                .status(epResponseStatus.getRequestIsInvalid())
                .build();
        FollowTable followTable = FollowTable.builder()
                .following(followingUser.getUserId())
                .follower(followerUser.getUserId())
                .isActive(false)
                .build();
        repository.save(followTable);
        //Burda istifadecinin Notification tablosunda istifadecinin adina notification qoyuruq
        return epResponseModel.<String>builder()
                .response("Success")
                .status(epResponseStatus.getSuccess())
                .build();
    }



    @Override
    public epResponseModel<String> unfollowUser(epJustJwt data, String followingUsername) {
        String followerId = jwtProvider.getSubject(data.getJwt());
        AppUser followingUser = userService.findUserByUsername(followingUsername);
        FollowTable followTable = repository.findFollowTableByFollowerAndFollowing(followerId, followingUser.getUserId());
        if(followerId == null || followingUser == null || followTable == null )
            return epResponseModel.<String>builder()
                    .response("Unknown Token")
                    .status(epResponseStatus.getRequestIsInvalid())
                    .build();
        repository.delete(followTable);
        return epResponseModel.<String>builder()
                .response("Success")
                .status(epResponseStatus.getSuccess())
                .build();
    }


    @Override
    public epResponseModel<String> userAcceptedFollowRequest(epSingleData data) {
        AppUser user = userService.findAppUserByUserId(jwtProvider.getSubject(data.getJwt()));
        FollowTable followTable = repository.findFollowTableById(data.getData());
        if(user == null || followTable == null || !user.isNotLocked() || !followTable.getFollowing().equals(user.getUserId()))
            return epResponseModel.<String>builder()
                    .response("Unknown Token")
                    .status(epResponseStatus.getRequestIsInvalid())
                    .build();
        followTable.setActive(true);
        repository.save(followTable);
        return epResponseModel.<String>builder()
                .response("Success")
                .status(epResponseStatus.getSuccess())
                .build();
    }


    @Override
    public epResponseModel<String> deleteFollowRequest(epSingleData data) {
        AppUser user = findUserWithJwt(data.getJwt());
        FollowTable followTable = repository.findFollowTableByFollowerAndFollowing(user.getUserId(), data.getData());
        if(user == null || followTable == null || !user.isNotLocked() || !followTable.getFollowing().equals(user.getUserId()))
            return epResponseModel.<String>builder()
                    .response("Unknown Token")
                    .status(epResponseStatus.getRequestIsInvalid())
                    .build();
        repository.delete(followTable);
        return epResponseModel.<String>builder()
                .response("Success")
                .status(epResponseStatus.getSuccess())
                .build();
    }

    @Override
    public epResponseModel<String> userDeniedFollowRequest(epSingleData data) {
        AppUser user = findUserWithJwt(data.getJwt());
        FollowTable followTable = repository.findFollowTableById(data.getData());
        if(user == null || followTable == null || !user.isNotLocked() || !followTable.getFollowing().equals(user.getUserId()))
            return epResponseModel.<String>builder()
                    .response("Unknown Token")
                    .status(epResponseStatus.getRequestIsInvalid())
                    .build();
        repository.delete(followTable);
        //
        // Burda Notificationun Xarici Xidmet metodalari vasitesi ile uygun bildiris silinmelidir
        //
        return epResponseModel.<String>builder()
                .response("Success")
                .status(epResponseStatus.getSuccess())
                .build();
    }


    @Override
    public epResponseModel<List<FollowUser>> getUserFollowers(epDoubleData data, String followingId) {
        return null;
    }


    //Xidmetci metodlar
    private AppUser findUserWithJwt(String jwt){
        return userService.findAppUserByUserId(jwtProvider.getSubject(jwt));
    }
}
