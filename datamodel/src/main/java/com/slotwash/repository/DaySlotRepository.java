package com.slotwash.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.slotwash.models.DaySlot;
import com.slotwash.models.DaySlot.SlotId;

public interface DaySlotRepository extends MongoRepository<DaySlot, SlotId>{
    
} 