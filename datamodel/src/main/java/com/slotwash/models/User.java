package com.slotwash.models;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user")
public class User implements Serializable{

    @Id
    private String userName;
    private String emailAddress;
    private GeoLocation location;
    private List<CustomRole> roles;
    private String password;
    private String phNo;
    private Boolean enabled;

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public GeoLocation getLocation() {
        return this.location;
    }

    public void setLocation(GeoLocation location) {
        this.location = location;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhNo() {
        return this.phNo;
    }

    public void setPhNo(String phNo) {
        this.phNo = phNo;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
    
    public List<CustomRole> getRoles() {
        return roles;
    }

    public void setRoles(List<CustomRole> roles) {
        this.roles = roles;
    }

}
