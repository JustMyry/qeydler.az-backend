package com.exnotis.backend.note.repository;

import com.exnotis.backend.note.model.NoteTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface NoteTagRepository extends JpaRepository<NoteTag, Long> {

    NoteTag findNoteTagByTag(String tag);

}
