package com.exnotis.backend.note.service.impl;


import com.exnotis.backend.note.model.NoteTag;
import com.exnotis.backend.note.repository.NoteTagRepository;
import com.exnotis.backend.note.service.NoteTagService;
import org.springframework.stereotype.Service;

@Service
public class NoteTagServiceImpl implements NoteTagService {

    private final NoteTagRepository repository;
    public NoteTagServiceImpl(NoteTagRepository repository){
        this.repository = repository;
    }


    @Override
    public NoteTag findTagByTag(String tag) {
        return repository.findNoteTagByTag(tag);
    }

    @Override
    public boolean isTagExists(String tag) {
        return false;
    }

    @Override
    public void saveTag(NoteTag tag) {
        repository.save(tag);
    }
}
