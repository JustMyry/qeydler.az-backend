package com.exnotis.backend.routine.model;

import com.exnotis.backend.epmodel.BaseModel;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Routine extends BaseModel {

    private String ownerId;

    private String routineName;

    private String routineContent;

    private String startHour;

    private String finishHour;

    private LocalDate routineDay;

    private String privacy;

    private Boolean isActive;






    //Getter and Setters
    public String getRoutineName() {
        return routineName;
    }

    public void setRoutineName(String routineName) { this.routineName = routineName; }

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

    public LocalDate getRoutineDay() {
        return routineDay;
    }

    public void setRoutineDay(LocalDate routineDay) {
        this.routineDay = routineDay;
    }


    public String getPrivacy() { return privacy; }

    public void setPrivacy(String privacy) { this.privacy = privacy; }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    // To String for CheckUp
    @Override
    public String toString() {
        return "Routine{" +
                "routineName='" + routineName + '\'' +
                ", routineContent='" + routineContent + '\'' +
                ", startHour='" + startHour + '\'' +
                ", finishHour='" + finishHour + '\'' +
                ", routineDay=" + routineDay +
                ", privacy='" + privacy + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
