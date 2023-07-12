package com.exnotis.backend.test;

import com.exnotis.backend.epcommunication.response.epResponseModel;
import com.exnotis.backend.epcommunication.response.epResponseStatus;
import com.exnotis.backend.authority.repository.AuthRepository;
import com.exnotis.backend.security.jwt.JwtProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/")
    public String testOfApi(){
        return "Works";
    }

    private JwtProvider jwtProvider;
    private AuthRepository authRepository;
    public TestController(JwtProvider jwtProvider, AuthRepository authRepository) {
        this.jwtProvider=jwtProvider;
        this.authRepository=authRepository;
    }


    @GetMapping("/auth/get/{auth}")
    public String getAuthById(@PathVariable int auth){
        return authRepository.findById(auth).getAuthority();
    }


    @GetMapping("/user/test")
    public epResponseModel<String> test() {
        return epResponseModel.<String>builder()
                .response("geldi")
                .status(epResponseStatus.getSuccess())
                .build();
    }

    @GetMapping("/admin/test")
    public epResponseModel<String> secTest() {
        return epResponseModel.<String>builder()
                .response("geldi")
                .status(epResponseStatus.getSuccess())
                .build();
    }

}

