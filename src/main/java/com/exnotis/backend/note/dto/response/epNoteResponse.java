package com.exnotis.backend.note.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Builder
@Getter
@Setter
public class epNoteResponse {

    private String noteName;
    private String note;
    private List<String> tags = new ArrayList<>();
    private String color;

    private Date createdDate;
    private Date updatedDate;

}
