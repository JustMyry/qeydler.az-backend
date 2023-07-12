package com.exnotis.backend.epcommunication.request;


import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class epTripleData {

    @NonNull
    private String jwt;

    private String dataOne;
    private String dataTwo;
    private String dataThree;

}
