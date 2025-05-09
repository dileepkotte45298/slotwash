package com.slotwash.web.rest.dto;

public class Login {

    private String username;
    private String password;
    private Boolean rememberMe;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean isRememberMe() {
        return rememberMe;
    }


    public void setRememberMe(Boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

}

