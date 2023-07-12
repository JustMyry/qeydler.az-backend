package com.exnotis.backend.quote.service;

import com.exnotis.backend.epcommunication.request.epJustJwt;
import com.exnotis.backend.epcommunication.request.epSingleData;
import com.exnotis.backend.epcommunication.response.epResponseModel;
import com.exnotis.backend.quote.dto.epQuoteRequest;
import com.exnotis.backend.quote.dto.epQuoteResponse;
import com.exnotis.backend.quote.dto.epShowUserQuotes;
import com.exnotis.backend.quote.model.Quote;

import java.util.List;

public interface QuoteService {

    public epResponseModel<epQuoteResponse> addQuote(String userId, epQuoteRequest data);

    epResponseModel<List<Quote>> showAllQuotes();

    epResponseModel<List<epQuoteResponse>> showAllUserQuotes(String userId, epShowUserQuotes data);

    epResponseModel<epQuoteResponse> editQuote(Long quoteId, epQuoteRequest data);

    epResponseModel<String> deleteQuote(Long quoteId, epJustJwt data);

    epResponseModel<epQuoteResponse> showQuote(Long quoteId, epJustJwt data);

    epResponseModel<String> reportQuote(Long quoteId, epJustJwt data);

    Quote findQuoteById(Long id);
}
