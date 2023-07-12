package com.exnotis.backend.note.model;

import com.exnotis.backend.epmodel.BaseModel;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;


@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class NoteTag extends BaseModel {

    private String tag;



    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
