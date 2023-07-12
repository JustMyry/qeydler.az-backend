package com.exnotis.backend.like.model;


import com.exnotis.backend.epmodel.BaseModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class QuoteLike extends BaseModel {

    private Long likedQuoteId;
    private String likedUserId;



    //Getter Setters

    public Long getLikedQuoteId() {
        return likedQuoteId;
    }

    public void setLikedQuoteId(Long likedQuoteId) {
        this.likedQuoteId = likedQuoteId;
    }

    public String getLikedUserId() {
        return likedUserId;
    }

    public void setLikedUserId(String likedUserId) {
        this.likedUserId = likedUserId;
    }
}

//    @OneToMany(fetch = FetchType.EAGER)
//    @JoinTable(
//            joinColumns = @JoinColumn(name = "appUser_id"),
//            inverseJoinColumns = @JoinColumn(name = "liked_quote_id")
//    )
//    private Collection<AppUser> likedUsers = new ArrayList<>();