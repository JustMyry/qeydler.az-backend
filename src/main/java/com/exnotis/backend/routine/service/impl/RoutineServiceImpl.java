package com.exnotis.backend.routine.service.impl;

import com.exnotis.backend.epcommunication.request.epDoubleData;
import com.exnotis.backend.epcommunication.request.epJustJwt;
import com.exnotis.backend.epcommunication.request.epTripleData;
import com.exnotis.backend.epcommunication.response.epResponseModel;
import com.exnotis.backend.epcommunication.response.epResponseStatus;
import com.exnotis.backend.routine.dto.request.EpRoutineRequest;
import com.exnotis.backend.routine.dto.response.EpRoutineResponse;
import com.exnotis.backend.routine.mapper.RoutineMapper;
import com.exnotis.backend.routine.model.Routine;
import com.exnotis.backend.routine.repository.RoutineRepository;
import com.exnotis.backend.routine.service.RoutineService;
import com.exnotis.backend.security.jwt.JwtProvider;
import com.exnotis.backend.user.model.AppUser;
import com.exnotis.backend.user.service.impl.UserExternalServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class RoutineServiceImpl implements RoutineService {

    private final JwtProvider jwtProvider;
    private final RoutineRepository repository;
    private final UserExternalServiceImpl userService;
    public RoutineServiceImpl(JwtProvider jwtProvider, RoutineRepository repository, UserExternalServiceImpl userService){
        this.jwtProvider = jwtProvider;
        this.repository = repository;
        this.userService = userService;
    }



    @Override
    public epResponseModel<String> addRoutine(EpRoutineRequest data) {

        if(!verifyRoutineRequest(data))
            return epResponseModel.<String>builder()
                    .response(null)
                    .status(epResponseStatus.getRequestIsInvalid())
                    .build();
        String userId = jwtProvider.getSubject(data.getJwt());
        if(userId == null)
            return epResponseModel.<String>builder()
                    .response(null)
                    .status(epResponseStatus.getJwtIsInvalid())
                    .build();
        Routine routine = RoutineMapper.INSTANCE.getRoutineFromRequest(data);
        routine.setOwnerId(userId);
        routine.setActive(true);
        repository.save(routine);
        System.out.println("Routine, addRoutine()--> Saved Routine: " + routine + " of " + userId);
        return epResponseModel.<String>builder()
                .response("Success")
                .status(epResponseStatus.getSuccess())
                .build();
    }


    @Override
    public epResponseModel<EpRoutineResponse> editRoutine(Long routineId, EpRoutineRequest data) {
        if(!verifyRoutineRequest(data))
            return epResponseModel.<EpRoutineResponse>builder()
                    .response(null)
                    .status(epResponseStatus.getRequestIsInvalid())
                    .build();
        String userId = jwtProvider.getSubject(data.getJwt());
        if(userId == null)
            return epResponseModel.<EpRoutineResponse>builder()
                    .response(null)
                    .status(epResponseStatus.getJwtIsInvalid())
                    .build();
        Routine routine = repository.findRoutineById(routineId);
        if(!verifyOwner(routine, userId))
            return epResponseModel.<EpRoutineResponse>builder()
                    .response(null)
                    .status(epResponseStatus.getJwtIsInvalid())
                    .build();
        RoutineMapper.INSTANCE.transferReqToRoutine(data, routine);
        repository.save(routine);
        return epResponseModel.<EpRoutineResponse>builder()
                .response(RoutineMapper.INSTANCE.getResponseFromRoutine(routine))
                .status(epResponseStatus.getSuccess())
                .build();
    }


    @Override
    public epResponseModel<String> deleteRoutine(epJustJwt data, Long routineId) {
        String userId = jwtProvider.getSubject(data.getJwt());
        if(userId == null)
            return epResponseModel.<String>builder()
                    .response(null)
                    .status(epResponseStatus.getJwtIsInvalid())
                    .build();
        Routine routine = repository.findRoutineById(routineId);
        if(!verifyOwner(routine, userId))
            return epResponseModel.<String>builder()
                    .response(null)
                    .status(epResponseStatus.getJwtIsInvalid())
                    .build();
        routine.setActive(false);
        repository.save(routine);
        return epResponseModel.<String>builder()
                .response("Routine Deleted")
                .status(epResponseStatus.getSuccess())
                .build();
    }


    @Override
    public epResponseModel<List<EpRoutineResponse>> showMyRoutines(epDoubleData data, String date) {
        String userId = jwtProvider.getSubject(data.getJwt());
        if(userId == null)
            return epResponseModel.<List<EpRoutineResponse>>builder()
                    .response(null)
                    .status(epResponseStatus.getJwtIsInvalid())
                    .build();
        LocalDate localDate = LocalDate.parse( date );
        List<Routine> routinesOfDay = repository.findRoutinesByDayAndOwnerId(userId, localDate, 10, 0);
        return epResponseModel.<List<EpRoutineResponse>>builder()
                .response(routinesOfDay.stream().map(s->{
                    EpRoutineResponse r = RoutineMapper.INSTANCE.getResponseFromRoutine(s);
                    return r;
                }).collect(Collectors.toList()))
                .status(epResponseStatus.getSuccess())
                .build();
    }


    @Override
    public epResponseModel<List<EpRoutineResponse>> showUserRoutines(epDoubleData data, String date, String ownerId) {
        System.out.println("Routine, showUserRoutines()--> METHOD STARTED #################################################### ");
        String userId = jwtProvider.getSubject(data.getJwt());
        AppUser owner = userService.findAppUserByUserId(ownerId);
        LocalDate localDate = LocalDate.parse(date);
        if (userId == null || owner == null)
            return epResponseModel.<List<EpRoutineResponse>>builder()
                    .response(null)
                    .status(epResponseStatus.getRequestIsInvalid())
                    .build();
        List<Routine> ifFriend = friendPrivacyRoutines(owner.getUserId(), localDate, "onlyFriends");
        List<Routine> ifglobal = globalPrivacyRoutines(owner.getUserId(), localDate, "global");
        List<Routine> routines = null;
        if (!ifFriend.isEmpty()) {
            routines = Stream.concat(ifFriend.stream(), ifglobal.stream()).toList();
            System.out.println("Routine, showUserRoutines().!ifFriend.isEmpty()--> " + routines);
        }
        System.out.println("Routine, showUserRoutines()--> METHOD FINISHED #################################################### ");
        return epResponseModel.<List<EpRoutineResponse>>builder()
                .response(routines.stream().map(s -> {
                    EpRoutineResponse r = RoutineMapper.INSTANCE.getResponseFromRoutine(s);
                    return r;
                }).collect(Collectors.toList()))
                .status(epResponseStatus.getSuccess())
                .build();
    }




    /***
     * Burdan sonraki metodlar ana metodlara destek ucun 'Utility' metodlaridir
    */
    // RoutinRequestin STANDARTlara uygun olub olmadigini yoxlayir.
    // True dondururse dogrudur, false deyildir.
    private boolean verifyRoutineRequest(EpRoutineRequest request){
        try {
            if (request.getRoutineName().isEmpty())
                return false;
            String[] start = request.getStartHour().split(":");
            String[] finish = request.getFinishHour().split(":");
            int startHour = Integer.parseInt(start[0]);
            int finishHour = Integer.parseInt(finish[0]);
            int startMinute = Integer.parseInt(start[1]);
            int finishMinute = Integer.parseInt(finish[1]);
            if (!(startHour <= finishHour && finishMinute <= 60 && startMinute <= 60))
                return false;
            if(!(Integer.valueOf(String.valueOf(startHour) + String.valueOf(startMinute)) <= 2400 && Integer.valueOf(String.valueOf(finishHour) + String.valueOf(finishMinute)) <= 2400)) {
                System.out.println(false);
                return false;
            }
        }
        catch (Exception e){
            System.out.println("Routine, verifyRoutineRequest()--> EXCEPTION: " + e);
        }
        return true;
    }


    // Rutinin, verilen User'a aid olub olmadigini yoxlayir
    // True dondururse dogrudur, false deyildir.
    private boolean verifyOwner(Routine routine, String userId){
        return routine.getOwnerId().equals(userId);
    }


    private List<Routine> friendPrivacyRoutines(String ownerId, LocalDate localDate, String privacy) {
        List<Routine> routines = repository.findRoutinesByDayAndOwnerIdAndPrivacy(ownerId, localDate, privacy);
        System.out.println("Routine, friendPrivacyRoutines()--> " + routines);
        return routines;
    }

    private List<Routine> globalPrivacyRoutines(String ownerId, LocalDate localDate, String privacy) {
        List<Routine> routines = repository.findRoutinesByDayAndOwnerIdAndPrivacy(ownerId, localDate, privacy);
        System.out.println("Routine, globalPrivacyRoutines()--> " + routines);
        return routines;
    }
}
