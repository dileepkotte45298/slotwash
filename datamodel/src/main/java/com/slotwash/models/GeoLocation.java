package com.slotwash.models;

import java.io.Serializable;

public class GeoLocation implements Serializable{
    
    private Double latitude;
    private Double longitude;

    public Double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
