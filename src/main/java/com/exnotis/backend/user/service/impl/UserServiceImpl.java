package com.exnotis.backend.user.service.impl;


import com.exnotis.backend.achievement.model.Achievement;
import com.exnotis.backend.epcommunication.request.epJustJwt;
import com.exnotis.backend.epcommunication.request.epSingleData;
import com.exnotis.backend.epcommunication.response.epResponseModel;
import com.exnotis.backend.epcommunication.response.epResponseStatus;
import com.exnotis.backend.follow.service.impl.FollowExternalServiceImpl;
import com.exnotis.backend.user.dto.request.EpEditAccount;
import com.exnotis.backend.security.jwt.JwtProvider;
import com.exnotis.backend.user.dto.request.EpEditUserPassword;
import com.exnotis.backend.user.dto.response.EpAccount;
import com.exnotis.backend.user.mapper.UserMapper;
import com.exnotis.backend.user.model.AppUser;
import com.exnotis.backend.user.repository.UserRepository;
import com.exnotis.backend.user.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.exnotis.backend.constants.UserConstants.*;



/**
 * @Author: Exnotis Corporation.
 * @Target: Main Service Implementation Class for User Service API of exnotis.com.
 * @Comment: Hec bir serh yoxdur.
 * */

@Service
public class UserServiceImpl implements UserService {

    private final JwtProvider jwtProvider;
    private final UserRepository repository;
    private final BlockListServiceImpl blockListService;
    private final PasswordEncoder passwordEncoder;
    private final FollowExternalServiceImpl followService;
    public UserServiceImpl(JwtProvider jwtProvider, BlockListServiceImpl blockListService, UserRepository repository, PasswordEncoder passwordEncoder
                            , FollowExternalServiceImpl followService){
        this.jwtProvider=jwtProvider;
        this.blockListService=blockListService;
        this.repository=repository;
        this.passwordEncoder = passwordEncoder;
        this.followService = followService;
    }



    @Override
    public epResponseModel<EpAccount> getAccountInformation(String username, epJustJwt data) {
        String clientId = jwtProvider.getSubject(data.getJwt());
        AppUser user = findUserByUsername(username);
        if(user == null || isUserBlocked(clientId, user.getUserId()) || clientId == null || !user.isNotLocked()){
            return epResponseModel.<EpAccount>builder()
                    .response(null)
                    .status(epResponseStatus.getRequestIsInvalid())
                    .build();
        }
        if(clientId.equals(user.getUserId())){
            EpAccount userAccount = UserMapper.INSTANCE.getEpAccountWithEmailFromUser(user);
            setUserFollowStatistics(userAccount, user);
            userAccount.setUserAchievements(getUserAchievements(user));
            return epResponseModel.<EpAccount>builder()
                    .response(userAccount)
                    .status(epResponseStatus.getSuccess())
                    .build();
        }
        EpAccount externalUser = UserMapper.INSTANCE.getEpAccountWithoutEmailFromUser(user);
        setUserFollowStatistics(externalUser, user);
        externalUser.setUserAchievements(getUserAchievements(user));
        return epResponseModel.<EpAccount>builder()
                .response(externalUser)
                .status(epResponseStatus.getSuccess())
                .build();
    }


    @Override
    public epResponseModel<EpAccount> editUserBio(String username, EpEditAccount data) {
        AppUser user = findUserByUsername(username);
        if(user == null || !isJwtBelongToUser(user.getUserId(), data.getJwt()) || !user.isNotLocked()){
            return epResponseModel.<EpAccount>builder()
                    .response(null)
                    .status(epResponseStatus.builder().message(JWT_IS_NOT_BELONG_TO_USER).code(WARN_CODE).build())
                    .build();
        }
        UserMapper.INSTANCE.transferEditRequestToUser(data, user);
        saveUser(user);
        EpAccount selfAccount = UserMapper.INSTANCE.getEpAccountWithEmailFromUser(user);
        setUserFollowStatistics(selfAccount, user);
        return epResponseModel.<EpAccount>builder()
                .response(selfAccount)
                .status(epResponseStatus.getSuccess())
                .build();
    }


    @Override
    public epResponseModel<String> blockUser(String username, epJustJwt data) {
        AppUser responser = repository.findAppUserByUsername(username);
        AppUser requester = repository.findAppUserByUserId(jwtProvider.getSubject(data.getJwt()));
        if(responser == null || requester == null || !requester.isNotLocked() || !responser.isNotLocked()){
            return epResponseModel.<String>builder()
                    .response(null)
                    .status(epResponseStatus.getRequestIsInvalid())
                    .build();
        }
        blockListService.blockUser(responser.getUserId(), requester.getUserId());
        return epResponseModel.<String>builder()
                .response(null)
                .status(epResponseStatus.getSuccess())
                .build();
    }


    @Override
    public epResponseModel<String> unBlockUser(String username, epJustJwt data) {
        AppUser responser = repository.findAppUserByUsername(username);
        AppUser requester = repository.findAppUserByUserId(jwtProvider.getSubject(data.getJwt()));
        if(responser == null || requester == null || !requester.isNotLocked() || !responser.isNotLocked()){
            return epResponseModel.<String>builder()
                    .response(null)
                    .status(epResponseStatus.getRequestIsInvalid())
                    .build();
        }
        blockListService.unBlockUser(responser.getUserId(), requester.getUserId());
        return epResponseModel.<String>builder()
                .response(null)
                .status(epResponseStatus.getSuccess())
                .build();
    }


    @Override
    public epResponseModel<String> editUserPassword(String username, EpEditUserPassword epEditUserPassword) {
        AppUser user = repository.findAppUserByUsername(username);
        if(user == null || !isJwtBelongToUser(user.getUserId(), epEditUserPassword.getJwt()) || !user.isNotLocked())
            return epResponseModel.<String>builder()
                    .response(null)
                    .status(epResponseStatus.getRequestIsInvalid())
                    .build();
        if(passwordEncoder.matches(epEditUserPassword.getCurrentPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(epEditUserPassword.getNewPassword()));
            saveUser(user);
        }
        else
            return epResponseModel.<String>builder()
                    .response("Password is invalid!")
                    .status(epResponseStatus.getPassIsWrong())
                    .build();
        return epResponseModel.<String>builder()
                .response("Success")
                .status(epResponseStatus.getSuccess())
                .build();
    }


    @Override
    public epResponseModel<String> editUserPrivacy(String username, epSingleData data) {
        AppUser user = repository.findAppUserByUsername(username);
        if(user == null || !isJwtBelongToUser(user.getUserId(), data.getJwt()) || !checkPrivacyRequest(data.getData()) || !user.isNotLocked())
            return epResponseModel.<String>builder()
                    .response(null)
                    .status(epResponseStatus.getRequestIsInvalid())
                    .build();
        user.setPrivacy(data.getData());
        saveUser(user);
        return epResponseModel.<String>builder()
                .response("Success")
                .status(epResponseStatus.getSuccess())
                .build();
    }



    //==================================================================================================================
    /**
     * @Target: Burdan sonraki metodlar User Service ucun yazilmis yardimci metodlardir.
     * @Comment: Bunlarin xaricle baglanditi yoxdur / olmamalidir.
     * @Comment: Bunlar sadece oxunurlugu artirmaq ucundur
     * */

    @Override
    public AppUser getUserByUserId(String userId) {
        return repository.findAppUserByUserId(userId);
    }

    @Override
    public AppUser findAppUserByUserId(String userId) {
        return repository.findAppUserByUserId(userId);
    }

    @Override
    public void saveUser(AppUser user){
        repository.save(user);
    }

    @Override
    public AppUser findUserByUsername(String username) {
        return repository.findAppUserByUsername(username);
    }


    @Override
    public AppUser findAppUserByEmail(String email) {
        return repository.findAppUserByEmail(email);
    }


    private boolean isJwtBelongToUser(String userId, String jwt){
        return jwtProvider.getSubject(jwt).equals(userId);
    }

    public boolean isUserBlocked(String blocked, String blockedBy){
        return blockListService.findBlockListsByBlockedBY(blockedBy).stream().anyMatch((s) -> s.getBlocked().equals(blocked));
    }

    public List<Achievement> getUserAchievements(AppUser user){
        return null;
    }

    // Followu yazandan sonra bunuda duzelt
    public Integer getUserFollowingsCount(AppUser user) {
        return followService.getUserFollowingCount(user.getUserId());
    }


    // Followu yazandan sonra bunuda duzelt
    public Integer getUserFollowersCount(AppUser user) {
        return followService.getUserFollowerCount(user.getUserId());
    }

    private void setUserFollowStatistics(EpAccount account, AppUser user){
        account.setFollowers(getUserFollowersCount(user));
        account.setFollowings(getUserFollowingsCount(user));
    }

    private boolean checkPrivacyRequest(String data) {
        return data.equals("private") || data.equals("open");
    }

}

