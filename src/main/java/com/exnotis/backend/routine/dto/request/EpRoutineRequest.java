package com.exnotis.backend.routine.dto.request;

import lombok.*;


@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EpRoutineRequest {

    private String jwt;

    private String routineName;

    private String routineContent;

    private String startHour;

    private String finishHour;

    private String routineDay;

    private String privacy;




    //Getters and Setters
    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public String getRoutineName() {
        return routineName;
    }

    public void setRoutineName(String routineName) {
        this.routineName = routineName;
    }

    public String getRoutineContent() {
        return routineContent;
    }

    public void setRoutineContent(String routineContent) {
        this.routineContent = routineContent;
    }

    public String getStartHour() {
        return startHour;
    }

    public void setStartHour(String startHour) {
        this.startHour = startHour;
    }

    public String getFinishHour() {
        return finishHour;
    }

    public void setFinishHour(String finishHour) {
        this.finishHour = finishHour;
    }

    public String getRoutineDay() {
        return routineDay;
    }

    public void setRoutineDay(String routineDay) {
        this.routineDay = routineDay;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }
}
