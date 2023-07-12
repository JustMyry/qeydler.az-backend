package com.exnotis.backend.report.repository;


import com.exnotis.backend.report.model.QuoteReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuoteReportRepository extends JpaRepository<QuoteReport, Long> {

    List<QuoteReport> findAllByReportedQuoteId(Long quoteId);

    List<QuoteReport> findAllByReporterId(String reporterId);

    QuoteReport findQuoteReportById(Long id);

}
