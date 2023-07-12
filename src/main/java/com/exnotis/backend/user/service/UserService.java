package com.exnotis.backend.user.service;


import com.exnotis.backend.epcommunication.request.epJustJwt;
import com.exnotis.backend.epcommunication.request.epSingleData;
import com.exnotis.backend.epcommunication.response.epResponseModel;
import com.exnotis.backend.user.dto.request.EpEditAccount;
import com.exnotis.backend.user.dto.request.EpEditUserPassword;
import com.exnotis.backend.user.model.AppUser;

import java.util.Optional;

public interface UserService {
    epResponseModel getAccountInformation(String userId, epJustJwt data);

    AppUser getUserByUserId(String userId);

    epResponseModel editUserBio(String userId, EpEditAccount data);

    AppUser findAppUserByUserId(String userId);

    void saveUser(AppUser user);

    AppUser findUserByUsername(String username);

    AppUser findAppUserByEmail(String email);

    epResponseModel<String> blockUser(String userId, epJustJwt data);

    epResponseModel<String> unBlockUser(String userId, epJustJwt data);

    epResponseModel<String> editUserPassword(String username, EpEditUserPassword userPassword);

    epResponseModel<String> editUserPrivacy(String username, epSingleData data);
}
