package com.exnotis.backend.like.service;

import com.exnotis.backend.epcommunication.request.epJustJwt;
import com.exnotis.backend.epcommunication.response.epResponseModel;

import java.util.List;

public interface LikeService {


    // Quote Like
    epResponseModel<String> likeQuote(Long id, epJustJwt data);

    epResponseModel<String> unlikeQuote(Long id, epJustJwt data);


}
