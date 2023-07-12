package com.exnotis.backend.note.service;

import com.exnotis.backend.note.model.Note;
import com.exnotis.backend.note.model.NoteTag;

public interface NoteTagService {

    NoteTag findTagByTag(String tag);

    boolean isTagExists(String tag);

    void saveTag(NoteTag tag);

}
