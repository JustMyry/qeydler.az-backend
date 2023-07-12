package com.exnotis.backend.like.service.impl;

import com.exnotis.backend.like.repository.QuoteLikeRepository;
import com.exnotis.backend.like.service.LikeExternalService;
import org.springframework.stereotype.Service;


@Service
public class LikeExternalServiceImpl implements LikeExternalService {

    private final QuoteLikeRepository quoteLikeRepository;
    public LikeExternalServiceImpl(QuoteLikeRepository quoteLikeRepository){
        this.quoteLikeRepository = quoteLikeRepository;
    }


    @Override
    public int quoteLikeCount(Long id) {
        return quoteLikeRepository.findAllByLikedQuoteId(id).size();
    }

}
