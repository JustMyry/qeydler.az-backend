package com.exnotis.backend.quote.model;


import com.exnotis.backend.epmodel.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Quote extends BaseModel {

    @NonNull
    @Column(nullable = false)
    private String authorUserId;

    @NonNull
    @Column(nullable = false)
    private String quoteHead;

    @NonNull
    @Column(nullable = false,  length = 500)
    private String quote;

    private Boolean isLocked;

    private Boolean isActive;


    //Getter Setter
    public String getQuoteHead() { return quoteHead; }

    public void setQuoteHead(String quoteHead) { this.quoteHead = quoteHead; }

    public String getQuote() { return quote; }

    public void setQuote(String quote) { this.quote = quote; }

    public String getAuthorUserId() { return authorUserId; }

    public void setAuthorUserId(String authorUserId) { this.authorUserId = authorUserId; }

    public Boolean getIsLocked() { return isLocked; }

    public void setIsLocked(Boolean isLocked) { this.isLocked = isLocked; }

    public Boolean getLocked() {
        return isLocked;
    }

    public void setLocked(Boolean locked) {
        isLocked = locked;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}