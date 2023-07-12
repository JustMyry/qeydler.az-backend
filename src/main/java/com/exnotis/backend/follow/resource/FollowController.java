package com.exnotis.backend.follow.resource;


import com.exnotis.backend.epcommunication.request.epDoubleData;
import com.exnotis.backend.epcommunication.request.epJustJwt;
import com.exnotis.backend.epcommunication.request.epSingleData;
import com.exnotis.backend.epcommunication.response.epResponseModel;
import com.exnotis.backend.follow.dto.response.FollowUser;
import com.exnotis.backend.follow.service.FollowService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/follow")
public class FollowController {

    private final FollowService service;
    public FollowController(FollowService service){
        this.service = service;
    }



    @PostMapping("/follow")
    public epResponseModel<String> followUser(@RequestBody epJustJwt data, @RequestParam("user") String followingId){
        return service.followUser(data, followingId);
    }

    @PostMapping("/accepted")
    public epResponseModel<String> userAcceptedFollowRequest(@RequestBody epSingleData data){
        return service.userAcceptedFollowRequest(data);
    }

    @PostMapping("/denied")
    public epResponseModel<String> userDeniedFollowRequest(@RequestBody epSingleData data){
        return service.userDeniedFollowRequest(data);
    }

    @PostMapping("/delete/request")
    public epResponseModel<String> deleteFollowRequest(@RequestBody epSingleData data){
        return service.deleteFollowRequest(data);
    }

    @PostMapping("/unfollow")
    public epResponseModel<String> unFollowUser(@RequestBody epJustJwt data, @RequestParam("user") String followingUsername){
        return service.unfollowUser(data, followingUsername);
    }


    @PostMapping("/get/followers")
    public epResponseModel<List<FollowUser>> getUserFollowers(@RequestBody epDoubleData data, @RequestParam("user") String followingId){
        return service.getUserFollowers(data, followingId);
    }


    @PostMapping("/get/followings")
    public epResponseModel<List<FollowUser>> getUserFollowings(@RequestBody epDoubleData data, @RequestParam("user") String followerId){
        return null;
    }

}
