package com.slotwash.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(PublicResource.class);

    @GetMapping("/ping")
    public  ResponseEntity<String> ping(){
        return ResponseEntity.ok("OK");
    }
    
}
