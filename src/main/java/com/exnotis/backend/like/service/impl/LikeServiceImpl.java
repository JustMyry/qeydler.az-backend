package com.exnotis.backend.like.service.impl;


import com.exnotis.backend.epcommunication.request.epJustJwt;
import com.exnotis.backend.epcommunication.response.epResponseModel;
import com.exnotis.backend.epcommunication.response.epResponseStatus;
import com.exnotis.backend.like.model.QuoteLike;
import com.exnotis.backend.like.repository.QuoteLikeRepository;
import com.exnotis.backend.like.service.LikeService;
import com.exnotis.backend.quote.model.Quote;
import com.exnotis.backend.quote.service.impl.QuoteServiceImpl;
import com.exnotis.backend.security.jwt.JwtProvider;
import com.exnotis.backend.user.service.impl.UserExternalServiceImpl;
import com.exnotis.backend.user.service.impl.UserServiceImpl;
import org.springframework.stereotype.Service;

import static com.exnotis.backend.constants.UserConstants.*;
import static com.exnotis.backend.constants.UserConstants.ACCOUNT_BLOCKED_BY_USER_CODE;

@Service
public class LikeServiceImpl implements LikeService {

    private final QuoteLikeRepository quoteLikeRepository;
    private final UserExternalServiceImpl userService;
    private final JwtProvider jwtProvider;
    private final QuoteServiceImpl quoteService;
    public LikeServiceImpl(QuoteLikeRepository quoteLikeRepository, JwtProvider jwtProvider, UserExternalServiceImpl userService, QuoteServiceImpl quoteService){
        this.quoteLikeRepository = quoteLikeRepository;
        this.userService = userService;
        this.jwtProvider = jwtProvider;
        this.quoteService = quoteService;
    }


    @Override
    public epResponseModel<String> likeQuote(Long id, epJustJwt data) {
        String clientId = jwtProvider.getSubject(data.getJwt());
        if(clientId.isEmpty())
            return epResponseModel.<String>builder()
                        .response(null)
                        .status(epResponseStatus.builder().message(JWT_IS_NOT_BELONG_TO_USER).code(WARN_CODE).build())
                        .build();
        Quote quote = quoteService.findQuoteById(id);
        if(quote==null)
            return epResponseModel.<String>builder()
                    .response(null)
                    .status(epResponseStatus.builder().message(QUOTE_WAS_NOT_FOUND).code(QUOTE_WAS_NOT_FOUND_CODE).build())
                    .build();
        if(userService.isUserBlocked(quote.getAuthorUserId(), clientId))
            return epResponseModel.<String>builder()
                    .response(null)
                    .status(epResponseStatus.builder().message(ACCOUNT_BLOCKED_BY_USER).code(ACCOUNT_BLOCKED_BY_USER_CODE).build())
                    .build();
        QuoteLike like = QuoteLike.builder()
                .likedQuoteId(id)
                .likedUserId(clientId)
                .build();
        quoteLikeRepository.save(like);
        return epResponseModel.<String>builder()
                .response(null)
                .status(epResponseStatus.getSuccess())
                .build();
    }


    @Override
    public epResponseModel<String> unlikeQuote(Long id, epJustJwt data) {
        String clientId = jwtProvider.getSubject(data.getJwt());
        if(clientId.isEmpty())
            return epResponseModel.<String>builder()
                    .response(null)
                    .status(epResponseStatus.builder().message(JWT_IS_NOT_BELONG_TO_USER).code(WARN_CODE).build())
                    .build();

        QuoteLike like = quoteLikeRepository.findQuoteLikeById(id);
        if(like==null)
            return epResponseModel.<String>builder()
                    .response(null)
                    .status(epResponseStatus.builder().message(GENERAL_FAULT).code(GENERAL_FAULT_CODE).build())
                    .build();
        quoteLikeRepository.delete(like);
        return epResponseModel.<String>builder()
                .response(null)
                .status(epResponseStatus.getSuccess())
                .build();
    }




}
