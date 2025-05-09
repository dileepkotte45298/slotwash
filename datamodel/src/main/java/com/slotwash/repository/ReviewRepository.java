package com.slotwash.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.slotwash.models.Review;

public interface ReviewRepository extends MongoRepository<Review, String>{
    
}
