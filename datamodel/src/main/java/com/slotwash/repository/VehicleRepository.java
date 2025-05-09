package com.slotwash.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.slotwash.models.Vehicle;

public interface VehicleRepository extends MongoRepository<Vehicle, String>{
    
}
