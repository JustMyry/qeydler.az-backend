package com.exnotis.backend.routine.mapper;


import com.exnotis.backend.routine.dto.request.EpRoutineRequest;
import com.exnotis.backend.routine.dto.response.EpRoutineResponse;
import com.exnotis.backend.routine.model.Routine;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper(componentModel = "spring")
public interface RoutineMapper {

    RoutineMapper INSTANCE = getMapper(RoutineMapper.class);


    @Mapping(source = "routineName", target = "routineName")
    @Mapping(source = "routineContent", target = "routineContent")
    @Mapping(source = "startHour", target = "startHour")
    @Mapping(source = "finishHour", target = "finishHour")
    @Mapping(source = "privacy", target = "privacy")
    Routine getRoutineFromRequest(EpRoutineRequest request);


//    @Mapping(source = "routineName", target = "routineName")
//    @Mapping(source = "routineContent", target = "routineContent")
//    @Mapping(source = "startHour", target = "startHour")
//    @Mapping(source = "finishHour", target = "finishHour")
//    @Mapping(source = "privacy", target = "privacy")
    EpRoutineResponse getResponseFromRoutine(Routine routine);


    void transferReqToRoutine(EpRoutineRequest request, @MappingTarget Routine routine);



}
