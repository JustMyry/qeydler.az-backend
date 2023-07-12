package com.exnotis.backend.auth.service;

import com.exnotis.backend.auth.dto.epSignIn;
import com.exnotis.backend.auth.dto.epSignUp;
import com.exnotis.backend.epcommunication.response.epResponseModel;

import javax.mail.MessagingException;

public interface AuthService {

    epResponseModel<String> signUpUser(epSignUp request) throws MessagingException;

    epResponseModel<String> signUpAdmin(epSignUp request);

    epResponseModel<String> signInUser(epSignIn request);

    epResponseModel<String> activateUser(String userid, String code);
}

