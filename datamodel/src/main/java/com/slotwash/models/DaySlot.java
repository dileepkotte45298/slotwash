package com.slotwash.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "day_slot")
public class DaySlot {

    public static class SlotId implements Serializable {

        @Field(order = 1)
        protected String ServiceProviderId;

        @Field(order = 2)
        protected long dayStart;
    }

    public static class Slot {

        private long start;
        private long finish;
        private Integer twoWheeler;
        private Integer fourWheeler;
        private Integer twoWheelerBooked;
        private Integer fourWheelerBooked;

        public long getStart() {
            return start;
        }

        public void setStart(long start) {
            this.start = start;
        }

        public long getFinish() {
            return finish;
        }

        public void setFinish(long finish) {
            this.finish = finish;
        }

        public Integer getTwoWheeler() {
            return twoWheeler;
        }

        public void setTwoWheeler(Integer twoWheeler) {
            this.twoWheeler = twoWheeler;
        }

        public Integer getFourWheeler() {
            return fourWheeler;
        }

        public void setFourWheeler(Integer fourWheeler) {
            this.fourWheeler = fourWheeler;
        }

        public Integer getTwoWheelerBooked() {
            return twoWheelerBooked;
        }

        public void setTwoWheelerBooked(Integer twoWheelerBooked) {
            this.twoWheelerBooked = twoWheelerBooked;
        }

        public Integer getFourWheelerBooked() {
            return fourWheelerBooked;
        }

        public void setFourWheelerBooked(Integer fourWheelerBooked) {
            this.fourWheelerBooked = fourWheelerBooked;
        }
    }

    @Id
    private SlotId id = new SlotId();
    private Boolean status;
    private List<Slot> slots = new ArrayList<>();

    public SlotId getId() {
        return id;
    }

    public void setId(SlotId id) {
        this.id = id;
    }

    public Boolean getStatus(){
        return status;
    }

    public void setStatus(Boolean status){
        this.status = status;
    }

    public List<Slot> getSlots(){
        return slots;
    }

    public void setSlots(List<Slot> slots){
        this.slots = slots;
    }
}
