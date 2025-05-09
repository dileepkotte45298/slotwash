package com.slotwash.models;

import org.springframework.data.mongodb.core.mapping.Document;

import com.slotwash.models.DaySlot.SlotId;

@Document(collection = "user_bookings")
public class Booking {

    public enum Status {
        CONFIRMED, CANCELLED, COMPLETED
    }

    private String userId;
    private String serviceProviderId;
    private String lpNo;
    private SlotId slotId;
    private long startTime;
    private long finishTIme;
    private Status status;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getServiceProviderId() {
        return serviceProviderId;
    }

    public void setServiceProviderId(String serviceProviderId) {
        this.serviceProviderId = serviceProviderId;
    }

    public String getLpNo() {
        return lpNo;
    }

    public void setLpNo(String lpNo) {
        this.lpNo = lpNo;
    }

    public SlotId getSlotId() {
        return slotId;
    }

    public void setSlotId(SlotId slotId) {
        this.slotId = slotId;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getFinishTIme() {
        return finishTIme;
    }

    public void setFinishTIme(long finishTIme) {
        this.finishTIme = finishTIme;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}
