package com.exnotis.backend.quote.dto;


import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class epShowUserQuotes {

    @NonNull
    private String jwt;

    private String userId;

    private int limit;
    private int offset;

}
