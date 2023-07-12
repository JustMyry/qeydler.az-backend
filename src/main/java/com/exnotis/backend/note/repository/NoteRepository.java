package com.exnotis.backend.note.repository;


import com.exnotis.backend.note.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {



    @Query(value = "SELECT * FROM note u WHERE u.owner_user_id=:userId AND u.is_active = true limit :limit offset :offset ", nativeQuery = true)
    public List<Note> findAllByOwnerUserId(@Param("userId") String userId, @Param("limit") int limit, @Param("offset") int offset);

    public Note findNoteById(Long id);

}
