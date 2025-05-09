package com.slotwash.models;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "service_provider")
public class ServiceProvider {

    private String venderName;
    private Address address;
    private String username;
    private String password;
    private List<CustomRole> roles;
    private List<String> images;

    public String getVenderName() {
        return venderName;
    }

    public void setVenderName(String venderName) {
        this.venderName = venderName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

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

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<CustomRole> getRoles() {
        return roles;
    }

    public void setRoles(List<CustomRole> roles) {
        this.roles = roles;
    }

}
