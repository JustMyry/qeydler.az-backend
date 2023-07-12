package com.exnotis.backend.report.service.impl;

import com.exnotis.backend.quote.repository.QuoteRepository;
import com.exnotis.backend.report.model.QuoteReport;
import com.exnotis.backend.report.repository.QuoteReportRepository;
import com.exnotis.backend.report.service.ReportService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ReportServiceImpl implements ReportService {

    private final QuoteReportRepository repository;
    ReportServiceImpl(QuoteReportRepository repository){
        this.repository = repository;
    }



    @Override
    public void reportQuote(String userId, Long quoteId) {
        QuoteReport quoteReport = QuoteReport.builder()
                .reporterId(userId)
                .reportedQuoteId(quoteId)
                .build();
        repository.save(quoteReport);
    }


    @Override
    public List<QuoteReport> reportedQuoteListById(Long id) {
        return repository.findAllByReportedQuoteId(id);
    }


    @Override
    public List<QuoteReport> reportedQuoteListByReporterId(String reporterId) {
        return repository.findAllByReporterId(reporterId);
    }


    @Override
    public void deleteQuoteReport(Long quoteId) {
        repository.delete(repository.findQuoteReportById(quoteId));
    }

    @Override
    public void saveQuote(QuoteReport report) {
        repository.save(report);
    }
}
