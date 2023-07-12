package com.exnotis.backend.quote.service.impl;

import com.exnotis.backend.quote.repository.QuoteRepository;
import com.exnotis.backend.quote.service.QuoteExternalService;

public class QuoteExternalServiceImpl implements QuoteExternalService {

    private final QuoteRepository repository;
    public QuoteExternalServiceImpl(QuoteRepository repository){
        this.repository = repository;
    }


    @Override
    public int userQuoteCount(String userId) {
        return repository.findAllByAuthorUserId(userId).size();
    }
}
