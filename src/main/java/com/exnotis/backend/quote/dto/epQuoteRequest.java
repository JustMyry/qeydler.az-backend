package com.exnotis.backend.quote.dto;

import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class epQuoteRequest {

    private String jwt;

    private String quoteHead;
    private String quote;
    private Boolean isLocked;

}
