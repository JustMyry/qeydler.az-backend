package com.exnotis.backend.follow.mapper;

import com.exnotis.backend.follow.dto.response.FollowUser;
import com.exnotis.backend.user.model.AppUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.factory.Mappers.getMapper;


@Mapper(componentModel = "spring")
public interface FollowDtoMapper {


    FollowDtoMapper INSTANCE = getMapper(FollowDtoMapper.class);


    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "profilePictureUrl", target = "profilePictureUrl")
    FollowUser getFollowUserFromUser(AppUser user);


}
