package com.exnotis.backend.user.mapper;


import com.exnotis.backend.user.dto.request.EpEditAccount;
import com.exnotis.backend.user.dto.request.EpEditUserPassword;
import com.exnotis.backend.user.dto.response.EpAccount;
import com.exnotis.backend.user.model.AppUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = getMapper(UserMapper.class);



    EpAccount getSelfAccountFromUser(AppUser appUser);


    @Mapping(source = "userBio", target = "userBio")
    @Mapping(source = "profilePictureUrl", target = "userProfilePicture")
    EpAccount getEpAccountWithEmailFromUser(AppUser appUser);

    @Mapping(source = "userBio", target = "userBio")
    @Mapping(target = "email", expression = "java(null)")
    @Mapping(source = "profilePictureUrl", target = "userProfilePicture")
    EpAccount getEpAccountWithoutEmailFromUser(AppUser appUser);


    void transferEditRequestToUser(EpEditAccount epEditAccount, @MappingTarget AppUser appUser);

    @Mapping(source = "newPassword", target = "password")
    void transferPasswordRequestToUser(EpEditUserPassword userPassword, @MappingTarget AppUser appUser);



}
