package com.exnotis.backend.epcommunication.request;


import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class epSingleData {

    @NonNull
    private String jwt;
    private String data;

}
