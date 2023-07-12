package com.exnotis.backend.routine.controller;


import com.exnotis.backend.epcommunication.request.epDoubleData;
import com.exnotis.backend.epcommunication.request.epJustJwt;
 import com.exnotis.backend.epcommunication.response.epResponseModel;
import com.exnotis.backend.routine.dto.request.EpRoutineRequest;
import com.exnotis.backend.routine.dto.response.EpRoutineResponse;
import com.exnotis.backend.routine.service.impl.RoutineServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/routine")
public class RoutineController {


    private final RoutineServiceImpl service;
    public RoutineController(RoutineServiceImpl service){
        this.service = service;
    }


    @PostMapping("/add")
    public epResponseModel<String> addRoutine(@RequestBody EpRoutineRequest data){
        return service.addRoutine(data);
    }


    @PostMapping("/edit/{routineId}")
    public epResponseModel<EpRoutineResponse> editRoutine(@RequestBody EpRoutineRequest data, @PathVariable Long routineId){
        return service.editRoutine(routineId, data);
    }


    @PostMapping("/delete/{routineId}")
    public epResponseModel<String> deleteRoutine(@RequestBody epJustJwt data, @PathVariable Long routineId ){
        return service.deleteRoutine(data, routineId);
    }


    @PostMapping("/show/mine/{date}")
    public epResponseModel<List<EpRoutineResponse>> showMyPrivateRoutines(@RequestBody epDoubleData data, @PathVariable String date){
        return service.showMyRoutines(data, date);
    }


    @PostMapping("/show/user/{date}")
    public epResponseModel<List<EpRoutineResponse>> showUserRoutines(@RequestBody epDoubleData data, @PathVariable String date, @RequestParam("ownerId") String ownerId){
        return service.showUserRoutines(data, date, ownerId);
    }




}
