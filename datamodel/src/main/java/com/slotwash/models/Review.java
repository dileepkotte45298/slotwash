package com.slotwash.models;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reviews")
public class Review {

    private String serviceProvideId;
    private String userId;
    private String comment;
    private Integer rating;
   
    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getServiceProvideId() {
        return serviceProvideId;
    }

    public void setServiceProvideId(String serviceProvideId) {
        this.serviceProvideId = serviceProvideId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
