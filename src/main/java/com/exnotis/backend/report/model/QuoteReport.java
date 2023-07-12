package com.exnotis.backend.report.model;


import com.exnotis.backend.epmodel.BaseModel;
import com.exnotis.backend.quote.model.Quote;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class QuoteReport extends BaseModel {

    private String reporterId;

    private Long reportedQuoteId;



    public Long getReportedQuoteId() {
        return reportedQuoteId;
    }

    public void setReportedQuoteId(Long reportedQuoteId) {
        this.reportedQuoteId = reportedQuoteId;
    }

    public String getReporterId() { return reporterId; }

    public void setReporterId(String reporterId) { this.reporterId = reporterId; }
}