package com.exnotis.backend.user.resource;


import com.exnotis.backend.epcommunication.request.epJustJwt;
import com.exnotis.backend.epcommunication.request.epSingleData;
import com.exnotis.backend.epcommunication.response.epResponseModel;
import com.exnotis.backend.user.dto.request.EpEditAccount;
import com.exnotis.backend.user.dto.request.EpEditUserPassword;
import com.exnotis.backend.user.dto.response.EpAccount;
import com.exnotis.backend.user.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/@")
public class UserController {

    private final UserService service;
    public UserController(UserService service){
        this.service=service;
    }


    // Hesaba basqa insan baxanda Fronta gostermesi ucun xarici hesabin istifadeciye gorunecek bilgilerini gonderir
    // Eger url'deki 'username' ile jwt'nin sahi oldugu id'e sahib 'username' eyni olarsa 'SelfAccount' bilgilerini gonderir
    @PostMapping("/{username}")
    public epResponseModel<Optional> getExternalAccountInformations(@PathVariable String username, @RequestBody epJustJwt data){
        return service.getAccountInformation(username, data);
    }


    @PostMapping("/{username}/edit")
    public epResponseModel<EpAccount> editUserBio(@PathVariable String username, @RequestBody EpEditAccount data){
        return service.editUserBio(username, data);
    }


    @PostMapping("/{username}/block")
    public epResponseModel<String> blockUser(@PathVariable String username, @RequestBody epJustJwt data){
        return service.blockUser(username, data);
    }


    @PostMapping("/{username}/unblock")
    public epResponseModel<String> unBlockUser(@PathVariable String username, @RequestBody epJustJwt data){
        return service.unBlockUser(username, data);
    }


    @PostMapping("/{username}/pass")
    public epResponseModel<String> editUserPassword(@PathVariable String username, @RequestBody EpEditUserPassword epEditUserPassword){
        return service.editUserPassword(username, epEditUserPassword);
    }


    @PostMapping("/{username}/privacy")
    public epResponseModel<String> editUserPrivacy(@PathVariable String username, @RequestBody epSingleData data){
        return service.editUserPrivacy(username, data);
    }

}
