package com.slotwash.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.slotwash.models.Booking;

public interface BookingRepository extends MongoRepository<Booking, String>{

    
}