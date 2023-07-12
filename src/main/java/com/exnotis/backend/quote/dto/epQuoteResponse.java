package com.exnotis.backend.quote.dto;


import lombok.*;

import java.util.Date;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class epQuoteResponse {

    private String authorNick;
    private String authorUserId;
    private String authorProfilePictureUrl;

    private Long quoteId;
    private Date quoteCreatedDate;
    private String quoteHead;
    private String quote;
    private Integer likeCount;
    private Integer commentCount;

}
