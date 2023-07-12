package com.exnotis.backend.user.model;


import com.exnotis.backend.authority.model.Authority;
import com.exnotis.backend.epmodel.BaseModel;
import jakarta.persistence.*;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
@ToString
public class AppUser extends BaseModel implements Serializable {


    @Column(nullable = false, updatable = false)
    private String userId;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    private String verificationCode;
    private String userBio;
    private String profilePictureUrl;
    private Date lastLoginDate;
    private boolean isActive;
    private boolean isNotLocked;
    private String role;
    private String privacy;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            joinColumns = @JoinColumn(name = "appuser_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id")
    )
    private Collection<Authority> authorities = new ArrayList<>();

    // Constructors
    public AppUser(){}

    public AppUser(String userId, String username, String email, String password, String verificationCode,
                   String userBio, String profilePictureUrl, Date lastLoginDate, boolean isActive,
                   boolean isNotLocked, String role, String privacy, Collection<Authority> authorities) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.verificationCode = verificationCode;
        this.userBio = userBio;
        this.profilePictureUrl = profilePictureUrl;
        this.lastLoginDate = lastLoginDate;
        this.isActive = isActive;
        this.isNotLocked = isNotLocked;
        this.role = role;
        this.authorities = authorities;
        this.privacy = privacy;
    }


    // Getters and Setterss
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getUserBio() {
        return userBio;
    }

    public void setUserBio(String userBio) { this.userBio = userBio; }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isNotLocked() {
        return isNotLocked;
    }

    public void setNotLocked(boolean notLocked) {
        isNotLocked = notLocked;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Collection<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<Authority> authorities) {
        this.authorities = authorities;
    }

    public String getPrivacy() { return privacy; }

    public void setPrivacy(String privacy) { this.privacy = privacy; }
}

