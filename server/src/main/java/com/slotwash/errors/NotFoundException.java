package com.slotwash.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends CustomParameterException{

    public NotFoundException(String message, String[] params) {
        super(message, params);
    }
    
}