package com.exnotis.backend.like.resource;


import com.exnotis.backend.epcommunication.request.epJustJwt;
import com.exnotis.backend.epcommunication.response.epResponseModel;
import com.exnotis.backend.like.service.impl.LikeServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/like")
public class LikeController {

    private final LikeServiceImpl service;
    public LikeController(LikeServiceImpl service){
        this.service = service;
    }




    //Quote Like Resources

    @PostMapping("/quote")
    public epResponseModel<String> likeQuote(@RequestParam("id") Long id, @RequestBody epJustJwt data){
        return service.likeQuote(id, data);
    }


    @PostMapping("/quote/unlike")
    public epResponseModel<String> unlikeQuote(@RequestParam("id") Long id, @RequestBody epJustJwt data){
        return service.unlikeQuote(id, data);
    }


    //Routine Like Resources


}
