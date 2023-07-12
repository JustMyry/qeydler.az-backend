package com.exnotis.backend.user.service;

import com.exnotis.backend.user.model.AppUser;
import com.exnotis.backend.user.repository.UserRepository;

public interface UserExternalService {


    AppUser findAppUserByUserId(String userId);

    void saveUser(AppUser user);

    AppUser findUserByUsername(String username);

    AppUser findAppUserByEmail(String email);

    boolean isJwtBelongToUser(String userId, String jwt);

    boolean isUserBlocked(String blocked, String blockedBy);

    public AppUser getUserWithJwt(String jwt);

}
