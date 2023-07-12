package com.exnotis.backend.quote.resource;


import com.exnotis.backend.epcommunication.request.epJustJwt;
import com.exnotis.backend.epcommunication.request.epSingleData;
import com.exnotis.backend.epcommunication.response.epResponseModel;
import com.exnotis.backend.quote.dto.epQuoteRequest;
import com.exnotis.backend.quote.dto.epQuoteResponse;
import com.exnotis.backend.quote.dto.epShowUserQuotes;
import com.exnotis.backend.quote.model.Quote;
import com.exnotis.backend.quote.service.impl.QuoteServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quote")
public class QuoteController {

    private final QuoteServiceImpl service;
    public QuoteController(QuoteServiceImpl service){
        this.service = service;
    }


    @PostMapping("/show/{quoteId}")
    public epResponseModel<epQuoteResponse> showQuote(@PathVariable Long quoteId, @RequestBody epJustJwt data){
        return service.showQuote(quoteId, data);
    }


    @PostMapping("/add/{userId}")
    public epResponseModel<epQuoteResponse> addQuote(@PathVariable String userId, @RequestBody epQuoteRequest data){
        return service.addQuote(userId, data);
    }


    // Jwt iste, locked olanlar gorunmesin
    // Yox, bunu sadece sil ya da admin selahiyyetine al
    @GetMapping("/show/all")
    public epResponseModel<List<Quote>> showAllQuotes(){
        return service.showAllQuotes();
    }


    @PostMapping("/all/{userId}")
    public epResponseModel<List<epQuoteResponse>> showAllUserQuotes(@PathVariable String userId, @RequestBody epShowUserQuotes data){
        return service.showAllUserQuotes(userId, data);
    }


    @PostMapping("/delete/{quoteId}")
    public epResponseModel<String> deleteQuote(@PathVariable Long quoteId, @RequestBody epJustJwt data){
        return service.deleteQuote(quoteId, data);
    }


    @PostMapping("/edit/{quoteId}")
    public epResponseModel<epQuoteResponse> editQuote(@PathVariable Long quoteId, @RequestBody epQuoteRequest data){
        return service.editQuote(quoteId, data);
    }




    @PostMapping("/report/{quoteId}")
    public epResponseModel<String> reportQuote(@PathVariable Long quoteId, @RequestBody epJustJwt data){
        return service.reportQuote(quoteId, data);
    }





}
