package com.exnotis.backend.user.model;

import com.exnotis.backend.epmodel.BaseModel;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;


@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class BlockList extends BaseModel {

    private String blocked;
    private String blockedBY;



    public String getBlocked() {
        return blocked;
    }

    public void setBlocked(String blocked) {
        this.blocked = blocked;
    }

    public String getBlockedBY() {
        return blockedBY;
    }

    public void setBlockedBY(String blockedBY) {
        this.blockedBY = blockedBY;
    }


    @Override
    public String toString() {
        return "BlockList{" +
                "blocked='" + blocked + '\'' +
                ", blockedBY='" + blockedBY + '\'' +
                '}';
    }
}
