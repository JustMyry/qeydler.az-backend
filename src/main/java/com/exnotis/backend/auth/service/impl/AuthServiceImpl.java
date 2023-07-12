package com.exnotis.backend.auth.service.impl;


import com.exnotis.backend.auth.constants.SignConstantsCode;
import com.exnotis.backend.auth.constants.SignConstantsMessage;
import com.exnotis.backend.auth.model.ActivationCode;
import com.exnotis.backend.auth.repository.ActivationCodeRepository;
import com.exnotis.backend.auth.service.AuthService;
import com.exnotis.backend.auth.dto.epSignIn;
import com.exnotis.backend.auth.dto.epSignUp;
import com.exnotis.backend.email.EmailProvider;
import com.exnotis.backend.epcommunication.response.epResponseModel;
import com.exnotis.backend.epcommunication.response.epResponseStatus;
import com.exnotis.backend.exception.CustomException;
import com.exnotis.backend.exception.StatusCode;
import com.exnotis.backend.exception.StatusMessage;
import com.exnotis.backend.user.model.AppUser;
import com.exnotis.backend.authority.model.Authority;
import com.exnotis.backend.authority.repository.AuthRepository;
import com.exnotis.backend.security.model.AppUserPrincipal;
import com.exnotis.backend.security.jwt.JwtProvider;
import com.exnotis.backend.user.service.impl.UserServiceImpl;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.exnotis.backend.auth.constants.SignConstantsCode.*;
import static com.exnotis.backend.auth.constants.SignConstantsMessage.*;
import static com.exnotis.backend.auth.constants.SignConstantsMessage.ACTIVATION_CODE_IS_NOT_CORRECT;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserServiceImpl userService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthRepository authRepository;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;
    private final ActivationCodeRepository activationCodeRepository;
    private final EmailProvider emailProvider;
    public AuthServiceImpl(UserServiceImpl userService, ModelMapper modelMapper, PasswordEncoder passwordEncoder, AuthRepository authRepository,
                           JwtProvider jwtProvider, AuthenticationManager authenticationManager, ActivationCodeRepository activationCodeRepository,
                           EmailProvider emailProvider){
        this.userService=userService;
        this.modelMapper=modelMapper;
        this.passwordEncoder=passwordEncoder;
        this.authRepository=authRepository;
        this.jwtProvider=jwtProvider;
        this.authenticationManager=authenticationManager;
        this.activationCodeRepository=activationCodeRepository;
        this.emailProvider=emailProvider;
    }





    @Override
    public epResponseModel<String> signUpUser(epSignUp request) throws MessagingException {
        if(!isRequestUnique(request.getUsername(), request.getEmail())){
            return epResponseModel.<String>builder()
                    .response(null)
                    .status(epResponseStatus.builder().message(USERNAME_ALREADY_EXISTS).code(AUTH_SERVICE_INTERNAL_ERROR).build())
                    .build();
        }
        AppUser user = modelMapper.map(request, AppUser.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        setRoleToUser(user, USER);
        user.setUserId(generateUserId());
        user.setNotLocked(true);
        String activationCode = String.valueOf((int)(Math.random()*(10000-1+1)+1));
        user.setVerificationCode(activationCode);
        userService.saveUser(user);
        createActivationCode(activationCode, user.getUserId(), new Date(System.currentTimeMillis() + EXPIRATION_TIME_FOR_ACTIVATION_CODE));
        //emailProvider.sendActivationEmail(user.getUserId(), activationCode, user.getEmail());
        epResponseModel<String> build = epResponseModel.<String>builder()
                .response(null)
                .status(epResponseStatus.builder().message(SignConstantsMessage.EMAIL_SENT).code(SignConstantsCode.EMAIL_SENT).build())
                .build();
        return build;
    }




    @Override
    public epResponseModel<String> signUpAdmin(epSignUp request) {
        if(!isRequestUnique(request.getUsername(), request.getEmail())){
            return epResponseModel.<String>builder()
                    .response(null)
                    .status(epResponseStatus.builder().message(USERNAME_ALREADY_EXISTS).code(AUTH_SERVICE_INTERNAL_ERROR).build())
                    .build();
        }
        AppUser user = modelMapper.map(request, AppUser.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        setRoleToUser(user, ADMIN);
        user.setUserId(generateUserId());
        user.setNotLocked(true);
        userService.saveUser(user);
        AppUserPrincipal principal = new AppUserPrincipal(user);
        String token = jwtProvider.generateJWT(principal);
        return epResponseModel.<String>builder()
                .response(token)
                .status(epResponseStatus.getSuccess())
                .build();
    }



    @Override
    @Transactional
    public epResponseModel<String> signInUser(epSignIn request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            String response = jwtProvider.generateJWT(getPrincipal(request.getUsername()));
            return epResponseModel.<String>builder()
                    .response(response)
                    .status(epResponseStatus.getSuccess())
                    .build();
        }catch (Exception ex){
            System.out.println(ex);
            throw new CustomException(StatusMessage.USERNAME_OR_PASSWORD_IS_INVALID, StatusCode.USERNAME_OR_PASSWORD_IS_INVALID);
        }
    }

    @Override
    public epResponseModel<String> activateUser(String userid, String code) {
        if(!verifyActivationCode(userid, code)){
            return epResponseModel.<String>builder()
                    .response(null)
                    .status(epResponseStatus.builder().message(ACTIVATION_CODE_IS_NOT_CORRECT).code(com.exnotis.backend.auth.constants.SignConstantsCode.ACTIVATION_CODE_IS_NOT_CORRECT).build())
                    .build();
        }
        AppUserPrincipal principal = getPrincipalwithUserid(userid);
        String token = jwtProvider.generateJWT(principal);
        return epResponseModel.<String>builder()
                .response(token)
                .status(epResponseStatus.getSuccess())
                .build();
    }

    private boolean verifyActivationCode(String userid, String code) {
        ActivationCode activationCode = activationCodeRepository.findActivationCodeByUserName(userid);
        if(activationCode != null && activationCode.getCode().equals(code))
            return true;
        else
            return false;
    }


    // USER qeydiyyatdan kecerken bir defeye ozel cagirilir.
    // Meqsed qeydiyyatdan kecen USERe DBdan elaqesiz bir ID vermekdir
    // Burdaki meqsed ise USERin DB IDsinin tehlukesizlik meqsedi ile FrontEnd e bildirmemekdir.
    private String generateUserId(){
        return DateTimeFormatter.ISO_INSTANT.format(LocalDateTime.now().toInstant(ZoneOffset.UTC)).substring(2)
                .replace("T", "").replace("-", "").replace(":","").replace(".","");
    }


    // USER obyektinden UserDetails obyekti almaq ucun
    private AppUserPrincipal getPrincipal(String username){
        return new AppUserPrincipal(userService.findUserByUsername(username));
    }


    // USER obyektinin IDsinden UserDetails obyekti almaq ucun
    private AppUserPrincipal getPrincipalwithUserid(String userId){
        return new AppUserPrincipal(userService.findAppUserByUserId(userId));
    }


    //Qeydiyyat isteyinde gelen 'username' in uygun olub olmadigini yoxlayiriq
    private boolean isRequestUnique(String username, String email) {
        System.out.println(userService.findUserByUsername(username));
        return userService.findUserByUsername(username)==null && userService.findAppUserByEmail(email)==null;
    }


    //Gonderilen 'User'e gonderilen adda 'Role'u DBdan cekib verir
    private void setRoleToUser(AppUser user, String role) {
        List<Authority> authorities = new ArrayList<>();
        authorities.add(authRepository.findByAuthority(role));
        user.setAuthorities(authorities);
    }


    //Istifadeci hesabini aktiv etsin deye aktivasiya kodu hazirlayir
    private void createActivationCode(String activationCode, String userid, Date date) {
        ActivationCode code = new ActivationCode(activationCode, userid, date);
        activationCodeRepository.save(code);
    }

}