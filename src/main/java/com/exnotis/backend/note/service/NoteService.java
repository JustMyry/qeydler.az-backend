package com.exnotis.backend.note.service;

import com.exnotis.backend.epcommunication.request.epJustJwt;
import com.exnotis.backend.epcommunication.response.epResponseModel;
import com.exnotis.backend.note.dto.request.epNoteRequest;
import com.exnotis.backend.note.dto.response.epNoteResponse;
import com.exnotis.backend.note.model.Note;

import java.util.List;

public interface NoteService {

    epResponseModel<List<epNoteResponse>> showAllNotes(epJustJwt data, int offset);

    epResponseModel<String> addNote(epNoteRequest data);

    epResponseModel<epNoteResponse> editNote(epNoteRequest data, Long noteId);

    public String testNote(epNoteRequest data);


    epResponseModel<String> deleteNote(epJustJwt data, Long noteId);

    epResponseModel<epNoteResponse> showNote(epJustJwt data, Long noteId);
}
