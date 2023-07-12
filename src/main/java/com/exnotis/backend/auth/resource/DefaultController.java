package com.exnotis.backend.auth.resource;

import com.exnotis.backend.auth.dto.epSignIn;
import com.exnotis.backend.auth.dto.epSignUp;
import com.exnotis.backend.epcommunication.response.epResponseModel;
import com.exnotis.backend.auth.service.AuthService;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@CrossOrigin
@RestController
@RequestMapping("/")
public class DefaultController {

    private AuthService service;
    public DefaultController(AuthService service){
        this.service=service;
    }

    @PostMapping("/signin")
    epResponseModel<String> signIn(@RequestBody epSignIn request){
        return service.signInUser(request);
    }


    @PostMapping("/signup/user")
    epResponseModel<String> signUpUser(@RequestBody epSignUp request) throws MessagingException {
        return service.signUpUser(request);
    }

    @PostMapping("/signup/admin")
    epResponseModel<String> signUpAdmin(@RequestBody epSignUp request){
        return service.signUpAdmin(request);
    }

    @GetMapping("/activate/{userid}")
    epResponseModel<String> activateUser(@PathVariable String userid, @RequestParam String code){
        return service.activateUser(userid, code);
    }

}
