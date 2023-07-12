package com.exnotis.backend.note.model;


import com.exnotis.backend.authority.model.Authority;
import com.exnotis.backend.epmodel.BaseModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Note extends BaseModel {

    @NonNull
    private  String ownerUserId;

    @NonNull
    private String noteName;

    @Column(length = 1000)
    private String note;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            joinColumns = @JoinColumn(name = "note_id"),
            inverseJoinColumns = @JoinColumn(name = "notetag_id")
    )
    private Collection<NoteTag> tags = new ArrayList<>();

    private String color;

    private boolean isActive;





    //Getters and Setters


    public String getOwnerUserId() {
        return ownerUserId;
    }

    public void setOwnerUserId(String ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    public String getNoteName() {
        return noteName;
    }

    public void setNoteName(String noteName) {
        this.noteName = noteName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Collection<NoteTag> getTags() {
        return tags;
    }

    public void setTags(Collection<NoteTag> tags) {
        this.tags = tags;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }


    @Override
    public String toString() {
        return "Note{" +
                "ownerUserId='" + ownerUserId + '\'' +
                ", noteName='" + noteName + '\'' +
                ", note='" + note + '\'' +
                ", tags=" + tags +
                ", color='" + color + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
