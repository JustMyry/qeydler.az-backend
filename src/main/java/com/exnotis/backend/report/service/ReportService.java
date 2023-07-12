package com.exnotis.backend.report.service;

import com.exnotis.backend.report.model.QuoteReport;

import java.util.List;

public interface ReportService {

    void reportQuote(String userId, Long quoteId);

    List<QuoteReport> reportedQuoteListById(Long id);

    List<QuoteReport> reportedQuoteListByReporterId(String reporterId);

    void deleteQuoteReport(Long quoteId);

    void saveQuote(QuoteReport report);
}
