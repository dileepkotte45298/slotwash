package com.slotwash.repository;

import com.slotwash.models.ServiceProvider;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ServiceProviderRepository extends MongoRepository<ServiceProvider, String>{

} 