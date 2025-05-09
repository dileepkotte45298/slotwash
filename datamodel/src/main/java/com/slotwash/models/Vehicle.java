package com.slotwash.models;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "vehicle")
public class Vehicle {

    public static enum VehicleType{
        CAR, BIKE , TRUCK 
    }

    @Id
    private String lpNo;
    private VehicleType vehicleType;
    private String userId;
    private List<String> images;

    public String getLpNo() {
        return lpNo;
    }

    public void setLpNo(String lpNo) {
        this.lpNo = lpNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }
}
