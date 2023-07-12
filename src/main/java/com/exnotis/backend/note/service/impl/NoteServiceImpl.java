package com.exnotis.backend.note.service.impl;

import com.exnotis.backend.epcommunication.request.epJustJwt;
import com.exnotis.backend.epcommunication.response.epResponseModel;
import com.exnotis.backend.epcommunication.response.epResponseStatus;
import com.exnotis.backend.note.dto.request.epNoteRequest;
import com.exnotis.backend.note.dto.response.epNoteResponse;
import com.exnotis.backend.note.model.Note;
import com.exnotis.backend.note.model.NoteTag;
import com.exnotis.backend.note.repository.NoteRepository;
import com.exnotis.backend.note.service.NoteService;
import com.exnotis.backend.security.jwt.JwtProvider;
import com.exnotis.backend.user.model.AppUser;
import com.exnotis.backend.user.service.impl.UserExternalServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.exnotis.backend.constants.UserConstants.JWT_IS_NOT_BELONG_TO_USER;
import static com.exnotis.backend.constants.UserConstants.WARN_CODE;


@Service
public class NoteServiceImpl implements NoteService {

    private final NoteRepository repository;
    private final NoteTagServiceImpl tagService;
    private final UserExternalServiceImpl userService;
    private final JwtProvider jwtProvider;
    public NoteServiceImpl(NoteRepository repository,NoteTagServiceImpl tagService, UserExternalServiceImpl userService, JwtProvider jwtProvider){
        this.repository = repository;
        this.userService = userService;
        this.jwtProvider = jwtProvider;
        this.tagService = tagService;

    }



    @Override
    public epResponseModel<String> addNote(epNoteRequest data) {
        if(!verifyRequest(data))
            return epResponseModel.<String>builder()
                    .response(null)
                    .status(epResponseStatus.getRequestIsInvalid())
                    .build();
        AppUser author = userService.getUserWithJwt(data.getJwt());
        List<NoteTag> tags = getTagLisFromRequest(data.getTags());
        Note note = Note.builder()
                .ownerUserId(author.getUserId())
                .isActive(true)
                .noteName(data.getNoteName())
                .note(data.getNote())
                .tags(tags)
                .build();
        repository.save(note);

        // Bu daxili metoddur. Response'da 'null', Status'da 'getSuccess' qaytarir.
        return epGetSuccess();
    }




    @Override
    public epResponseModel<List<epNoteResponse>> showAllNotes(epJustJwt data, int offset) {
        String userId = jwtProvider.getSubject(data.getJwt());
        if(userId.isEmpty())
            return epResponseModel.<List<epNoteResponse>>builder()
                    .response(null)
                    .status(epResponseStatus.builder().message(JWT_IS_NOT_BELONG_TO_USER).code(WARN_CODE).build())
                    .build();
        //Offset'in cox olmasi ehtimalini nezere almamisiq.
        List<epNoteResponse> responses = repository.findAllByOwnerUserId(userId, 10, offset).stream().map(s-> {
                    epNoteResponse n = epNoteResponse.builder()
                            .noteName(s.getNoteName())
                            .note(s.getNote())
                            .tags(s.getTags().stream().map(t->{return t.getTag();}).collect(Collectors.toList()))
                            .createdDate(s.getCreatedAt())
                            .updatedDate(s.getUpdatedAt())
                            .build();
                    return n;
                })
                .collect(Collectors.toList());
        return epResponseModel.<List<epNoteResponse>>builder()
                .response(responses)
                .status(epResponseStatus.getSuccess())
                .build();
    }


    @Override
    public epResponseModel<epNoteResponse> editNote(epNoteRequest data, Long noteId) {
        String userId = jwtProvider.getSubject(data.getJwt());
        if(userId.isEmpty())
            return epResponseModel.<epNoteResponse>builder()
                    .response(null)
                    .status(epResponseStatus.builder().message(JWT_IS_NOT_BELONG_TO_USER).code(WARN_CODE).build())
                    .build();
        Note note = repository.findNoteById(noteId);
        if(note == null)
            throw new RuntimeException("EXCEPTION::: Note, deleteNote() -> noteId is not valid, does not found anything id DB.");
        if(!note.getOwnerUserId().equals(userId))
            return epResponseModel.<epNoteResponse>builder()
                    .response(null)
                    .status(epResponseStatus.builder().message(JWT_IS_NOT_BELONG_TO_USER).code(WARN_CODE).build())
                    .build();
        note.setNoteName(data.getNoteName());
        note.setNote(data.getNote());
        note.setTags(getTagLisFromRequest(data.getTags()));
        note.setColor(data.getColor());

        // Mutleq buralari MAPPER ile ele
        epNoteResponse response = epNoteResponse.builder()
                .tags(note.getTags().stream().map(t->{return t.getTag();}).collect(Collectors.toList()))
                .noteName(note.getNoteName())
                .note(note.getNote())
                .color(note.getColor())
                .updatedDate(note.getUpdatedAt())
                .createdDate(note.getCreatedAt())
                .build();
        return epResponseModel.<epNoteResponse>builder()
                .response(response)
                .status(epResponseStatus.getSuccess())
                .build();
    }


    @Override
    public epResponseModel<String> deleteNote(epJustJwt data, Long noteId) {
        String userId = jwtProvider.getSubject(data.getJwt());
        if(userId.isEmpty())
            return epResponseModel.<String>builder()
                    .response(null)
                    .status(epResponseStatus.builder().message(JWT_IS_NOT_BELONG_TO_USER).code(WARN_CODE).build())
                    .build();
        Note note = repository.findNoteById(noteId);
        if(note == null)
            throw new RuntimeException("EXCEPTION::: Note, deleteNote() -> noteId is not valid, does not found anything id DB.");
        if(!note.getOwnerUserId().equals(userId))
            return epResponseModel.<String>builder()
                    .response(null)
                    .status(epResponseStatus.builder().message(JWT_IS_NOT_BELONG_TO_USER).code(WARN_CODE).build())
                    .build();
        repository.delete(note);
        return epGetSuccess();
    }


    @Override
    public epResponseModel<epNoteResponse> showNote(epJustJwt data, Long noteId) {
        String userId = jwtProvider.getSubject(data.getJwt());
        if(userId.isEmpty())
            return epResponseModel.<epNoteResponse>builder()
                    .response(null)
                    .status(epResponseStatus.builder().message(JWT_IS_NOT_BELONG_TO_USER).code(WARN_CODE).build())
                    .build();
        Note note = repository.findNoteById(noteId);
        if(note == null)
            throw new RuntimeException("EXCEPTION::: Note, deleteNote() -> noteId is not valid, does not found anything id DB.");
        if(!note.getOwnerUserId().equals(userId))
            return epResponseModel.<epNoteResponse>builder()
                    .response(null)
                    .status(epResponseStatus.builder().message(JWT_IS_NOT_BELONG_TO_USER).code(WARN_CODE).build())
                    .build();
        epNoteResponse response = epNoteResponse.builder()
                .tags(note.getTags().stream().map(t->{return t.getTag();}).collect(Collectors.toList()))
                .noteName(note.getNoteName())
                .note(note.getNote())
                .color(note.getColor())
                .updatedDate(note.getUpdatedAt())
                .createdDate(note.getCreatedAt())
                .build();
        return epResponseModel.<epNoteResponse>builder()
                .response(response)
                .status(epResponseStatus.getSuccess())
                .build();
    }


    /**Note Modulunda neyi ise yoxlamaq ucun umumi test metodudur.
     * Parametrleri ve Response her defe uygun gorevine gore deyise biler.
     * @param data
     * @return String */
    @Override
    public String testNote(epNoteRequest data) {
        System.out.println(data.getTags());
        return "success";
    }



    /* YARDIMDI METODLAR ############################################################################################ */


    /* Gelen Note 'edit', 'add' isteklerinde uygun olaraq - 'note', 'noteName', 'jwt' lerin
    *  bos olub-olmamasini yoxlayir. Uygun boolead deyer geri dondurur
    *  *** Bu metodun xarici baglantisi yoxdur / olmamalidir! */
    private boolean verifyRequest(epNoteRequest data){
        boolean result = !data.getNoteName().isEmpty() || !data.getNote().isEmpty() || !data.getJwt().isEmpty() ;
        System.out.println(result);
        return true;
    }




    /* Istekde gelen 'Note' isteyinden, tag'lari alib uygun 'NoteTag' nesnelerini ile bir Liste
    * doldurub geri donderir. Cunki gelen istek List<String>, teleb olunan List<NoteTag>dir.
    * Eger ele nesne yoxdursa qurur, varsa elave edir.
    * *** Bu metodun xarici baglantisi yoxdur / olmamalidir! */
    private List<NoteTag> getTagLisFromRequest(List<String> data){
        return data.stream().map(s-> {
            NoteTag tag = tagService.findTagByTag(s);
            if(tag == null){
                NoteTag t = new NoteTag().builder()
                        .tag(s)
                        .build();
                tagService.saveTag(t);
                return t;
            }else{
                return tag;
            }
        }).collect(Collectors.toList());
    }




    /* Sadece 'silme' 'elave etme' kimi emeliyyatlardan sonra qaytarilacaq 'SUCCESS' cavabini (response)
    *  daha dinamik etmek ucundur.
    *  *** Bu metodun xarici baglantisi yoxdur / olmamalidir! */
    private epResponseModel<String> epGetSuccess(){
        return epResponseModel.<String>builder()
                .response(null)
                .status(epResponseStatus.getSuccess())
                .build();
    }

}
