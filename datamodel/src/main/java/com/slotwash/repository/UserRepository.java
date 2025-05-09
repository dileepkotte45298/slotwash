package com.slotwash.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.slotwash.models.User;

public interface UserRepository extends MongoRepository<User, String>{
    
} 