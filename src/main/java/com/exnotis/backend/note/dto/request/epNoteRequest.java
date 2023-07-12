package com.exnotis.backend.note.dto.request;


import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class epNoteRequest {

    @NonNull
    private  String jwt;

    @NonNull
    private String noteName;

    @Column(nullable = false,  length = 1000)
    private String note;

    private List<String> tags = new ArrayList<>();
    private String color;

}
