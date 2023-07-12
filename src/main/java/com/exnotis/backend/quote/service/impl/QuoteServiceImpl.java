package com.exnotis.backend.quote.service.impl;

import com.exnotis.backend.epcommunication.request.epJustJwt;
import com.exnotis.backend.epcommunication.request.epSingleData;
import com.exnotis.backend.epcommunication.response.epResponseModel;
import com.exnotis.backend.epcommunication.response.epResponseStatus;
import com.exnotis.backend.like.service.impl.LikeExternalServiceImpl;
import com.exnotis.backend.like.service.impl.LikeServiceImpl;
import com.exnotis.backend.quote.dto.epQuoteRequest;
import com.exnotis.backend.quote.dto.epQuoteResponse;
import com.exnotis.backend.quote.dto.epShowUserQuotes;
import com.exnotis.backend.quote.model.Quote;
import com.exnotis.backend.quote.repository.QuoteRepository;
import com.exnotis.backend.quote.service.QuoteService;
import com.exnotis.backend.report.model.QuoteReport;
import com.exnotis.backend.report.service.impl.ReportServiceImpl;
import com.exnotis.backend.security.jwt.JwtProvider;
import com.exnotis.backend.user.model.AppUser;
import com.exnotis.backend.user.service.impl.BlockListServiceImpl;
import com.exnotis.backend.user.service.impl.UserExternalServiceImpl;
import com.exnotis.backend.user.service.impl.UserServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.exnotis.backend.constants.UserConstants.*;
import static com.exnotis.backend.constants.UserConstants.ACCOUNT_BLOCKED_BY_USER_CODE;


@Service
public class QuoteServiceImpl implements QuoteService {

    private final QuoteRepository repository;
    private final JwtProvider jwtProvider;
    private final BlockListServiceImpl blockListService;
    private final UserExternalServiceImpl userService;
    private final ReportServiceImpl reportService;
    private final LikeExternalServiceImpl likeService;
    public QuoteServiceImpl(QuoteRepository repository, ReportServiceImpl reportService, JwtProvider jwtProvider, BlockListServiceImpl blockListService,
                            UserExternalServiceImpl userService, LikeExternalServiceImpl likeService){
        this.jwtProvider = jwtProvider;
        this.repository = repository;
        this.blockListService = blockListService;
        this.userService = userService;
        this.reportService = reportService;
        this.likeService = likeService;
    }


    @Override
    public epResponseModel<epQuoteResponse> addQuote(String userId, epQuoteRequest data) {
        if(!jwtProvider.isJwtBelongToUser(userId, data.getJwt())){
            return epResponseModel.<epQuoteResponse>builder()
                    .response(null)
                    .status(epResponseStatus.builder().message(JWT_IS_NOT_BELONG_TO_USER).code(WARN_CODE).build())
                    .build();
        }
        Quote quote = Quote.builder()
                .authorUserId(userId)
                .isLocked(false)
                .quoteHead(data.getQuoteHead())
                .quote(data.getQuote())
                .build();
        repository.save(quote);
        return epResponseModel.<epQuoteResponse>builder()
                .response(null)
                .status(epResponseStatus.getSuccess())
                .build();
    }

    @Override
    public epResponseModel<List<Quote>> showAllQuotes() {
        List<Quote> response = repository.findAll(10, 0);

        return epResponseModel.<List<Quote>>builder()
                .response(response)
                .status(epResponseStatus.getSuccess())
                .build();
    }



    @Override
    public epResponseModel<List<epQuoteResponse>> showAllUserQuotes(String userId, epShowUserQuotes data) {
        if(!jwtProvider.isJwtBelongToUser(data.getUserId(), data.getJwt())){
            return epResponseModel.<List<epQuoteResponse>>builder()
                    .response(null)
                    .status(epResponseStatus.builder().message(JWT_IS_NOT_BELONG_TO_USER).code(WARN_CODE).build())
                    .build();
        }
        if(blockListService.isUserBlocked(data.getUserId(), userId)){
            return epResponseModel.<List<epQuoteResponse>>builder()
                    .response(null)
                    .status(epResponseStatus.builder().message(ACCOUNT_BLOCKED_BY_USER).code(ACCOUNT_BLOCKED_BY_USER_CODE).build())
                    .build();
        }
        AppUser user = userService.findAppUserByUserId(userId);
        if(user==null)
            return epResponseModel.<List<epQuoteResponse>>builder()
                    .response(null)
                    .status(epResponseStatus.builder().message(ACCOUNT_BLOCKED_BY_USER).code(ACCOUNT_BLOCKED_BY_USER_CODE).build())
                    .build();
        if(data.getLimit()>20)
            throw new IllegalArgumentException("Quote Service | showAllUserQuotes: Limit cant be higher than 20!");

        List<epQuoteResponse> response = repository.findAllByAuthorUserId(userId, data.getLimit(), data.getOffset()).stream().map(s-> {
                    epQuoteResponse n = new epQuoteResponse().builder()
                            .quoteId(s.getId())
                            .quoteCreatedDate(s.getCreatedAt())
                            .likeCount(likeService.quoteLikeCount(s.getId()))
                            .commentCount(21)
                            .quote(s.getQuote())
                            .quoteHead(s.getQuoteHead())
                            .authorNick(user.getUsername())
                            .authorProfilePictureUrl(user.getProfilePictureUrl())
                            .authorUserId(s.getAuthorUserId())
                            .build();
                    return n;
                })
                .collect(Collectors.toList());
        if (response.isEmpty())
            return epResponseModel.<List<epQuoteResponse>>builder()
                    .response(null)
                    .status(epResponseStatus.builder().message(QUOTE_WAS_NOT_FOUND).code(QUOTE_WAS_NOT_FOUND_CODE).build())
                    .build();

        return epResponseModel.<List<epQuoteResponse>>builder()
                .response(response)
                .status(epResponseStatus.getSuccess())
                .build();
    }



    @Override
    public epResponseModel<epQuoteResponse> editQuote(Long quoteId, epQuoteRequest data) {
        Quote quote = repository.findQuoteById(quoteId);
        if(quote==null)
            return epResponseModel.<epQuoteResponse>builder()
                    .response(null)
                    .status(epResponseStatus.builder().message(QUOTE_WAS_NOT_FOUND).code(QUOTE_WAS_NOT_FOUND_CODE).build())
                    .build();
        if(!quote.getAuthorUserId().equals(jwtProvider.getSubject(data.getJwt())))
            return epResponseModel.<epQuoteResponse>builder()
                    .response(null)
                    .status(epResponseStatus.builder().message(JWT_IS_NOT_BELONG_TO_USER).code(WARN_CODE).build())
                    .build();
        quote.setQuote(data.getQuote());
        quote.setQuoteHead(data.getQuoteHead());
        quote.setIsLocked(data.getIsLocked());
        repository.save(quote);
        return epResponseModel.<epQuoteResponse>builder()
                .response(null)
                .status(epResponseStatus.getSuccess())
                .build();
    }



    @Override
    public epResponseModel<String> deleteQuote(Long quoteId, epJustJwt data) {
        Quote quote = repository.findQuoteById(quoteId);
        if(quote==null)
            return epResponseModel.<String>builder()
                    .response(null)
                    .status(epResponseStatus.builder().message(QUOTE_WAS_NOT_FOUND).code(QUOTE_WAS_NOT_FOUND_CODE).build())
                    .build();
        if(!quote.getAuthorUserId().equals(jwtProvider.getSubject(data.getJwt())))
            return epResponseModel.<String>builder()
                    .response(null)
                    .status(epResponseStatus.builder().message(JWT_IS_NOT_BELONG_TO_USER).code(WARN_CODE).build())
                    .build();
        quote.setActive(false);
        repository.save(quote);
        return  epResponseModel.<String>builder()
                .response(null)
                .status(epResponseStatus.getSuccess())
                .build();
    }



    @Override
    public epResponseModel<epQuoteResponse> showQuote(Long quoteId, epJustJwt data) {
        Quote quote = repository.findQuoteById(quoteId);
        if(quote==null)
            return epResponseModel.<epQuoteResponse>builder()
                    .response(null)
                    .status(epResponseStatus.builder().message(QUOTE_WAS_NOT_FOUND).code(QUOTE_WAS_NOT_FOUND_CODE).build())
                    .build();
        AppUser user = userService.findAppUserByUserId(quote.getAuthorUserId());
        epQuoteResponse response = epQuoteResponse.builder()
                .authorUserId(user.getUserId())
                .authorProfilePictureUrl(user.getProfilePictureUrl())
                .authorNick(user.getUsername())
                .quoteId(quoteId)
                .quoteHead(quote.getQuoteHead())
                .quote(quote.getQuote())
                .commentCount(12)                                                               // Bunlara bax
                .likeCount(likeService.quoteLikeCount(quoteId))
                .quoteCreatedDate(quote.getCreatedAt())
                .build();
        return epResponseModel.<epQuoteResponse>builder()
                .response(response)
                .status(epResponseStatus.getSuccess())
                .build();
    }

    @Override
    public epResponseModel<String> reportQuote(Long quoteId, epJustJwt data) {
        String userId = jwtProvider.getSubject(data.getJwt());
        if(userId.isEmpty())
            return epResponseModel.<String>builder()
                    .response(null)
                    .status(epResponseStatus.builder().message(USER_WAS_NOT_FOUND).code(USER_WAS_NOT_FOUND_CODE).build())
                    .build();
        if(repository.findQuoteById(quoteId)==null)
            throw new IllegalArgumentException();
        QuoteReport report = QuoteReport.builder()
                .reportedQuoteId(quoteId)
                .reporterId(userId)
                .build();
        reportService.saveQuote(report);
        return epResponseModel.<String>builder()
                .response(null)
                .status(epResponseStatus.getSuccess())
                .build();
    }

    @Override
    public Quote findQuoteById(Long id) {
        return repository.findQuoteById(id);
    }
}
