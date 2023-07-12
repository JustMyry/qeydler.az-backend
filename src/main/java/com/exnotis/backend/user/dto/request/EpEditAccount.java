package com.exnotis.backend.user.dto.request;


import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class EpEditAccount {

    @NonNull
    private String jwt;

    private String username;
    private String email;
    private String profilePictureUrl;
    private String userBio;
    private String privacy;

}
