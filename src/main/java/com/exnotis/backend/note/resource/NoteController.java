package com.exnotis.backend.note.resource;


import com.exnotis.backend.epcommunication.request.epJustJwt;
import com.exnotis.backend.epcommunication.response.epResponseModel;
import com.exnotis.backend.note.dto.request.epNoteRequest;
import com.exnotis.backend.note.dto.response.epNoteResponse;
import com.exnotis.backend.note.service.impl.NoteServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/note")
public class NoteController {

    private final NoteServiceImpl service;
    public NoteController(NoteServiceImpl service){
        this.service = service;
    }




    @PostMapping("/add")
    public epResponseModel<String> addNote(@RequestBody epNoteRequest data){

        return service.addNote(data);
    }



    @PostMapping("/all")
    public epResponseModel<List<epNoteResponse>> showNotes(@RequestBody epJustJwt data, @RequestParam int offset){
        return service.showAllNotes(data, offset);
    }



    @PostMapping("/delete/{noteId}")
    public epResponseModel<String> deleteNote(@RequestBody epJustJwt data, @PathVariable Long noteId){

        return service.deleteNote(data, noteId);
    }


    @PostMapping("/@/{noteId}")
    public epResponseModel<epNoteResponse> showNote(@RequestBody epJustJwt data, @PathVariable Long noteId){
        return service.showNote(data, noteId);
    }


    @PostMapping("/edit/{noteId}")
    public epResponseModel<epNoteResponse> editNote(@RequestBody epNoteRequest data, @PathVariable Long noteId){
        return service.editNote(data, noteId);
    }


    @PostMapping("/test")
    public String testNote(@RequestBody epNoteRequest data){
        return service.testNote(data);
    }

}
