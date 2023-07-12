package com.exnotis.backend.routine.service;


import com.exnotis.backend.epcommunication.request.epDoubleData;
import com.exnotis.backend.epcommunication.request.epJustJwt;
import com.exnotis.backend.epcommunication.request.epTripleData;
import com.exnotis.backend.epcommunication.response.epResponseModel;
import com.exnotis.backend.routine.dto.request.EpRoutineRequest;
import com.exnotis.backend.routine.dto.response.EpRoutineResponse;

import java.util.List;

public interface RoutineService {

    epResponseModel<String> addRoutine(EpRoutineRequest data);

    epResponseModel<EpRoutineResponse> editRoutine(Long routineId, EpRoutineRequest data);

    epResponseModel<String> deleteRoutine(epJustJwt data, Long routineId);

    epResponseModel<List<EpRoutineResponse>> showMyRoutines(epDoubleData data, String date);

    epResponseModel<List<EpRoutineResponse>> showUserRoutines(epDoubleData data, String date, String userId);
}
