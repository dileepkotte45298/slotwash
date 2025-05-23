package com.slotwash.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends CustomParameterException{

    public BadRequestException(String message, String[] params) {
        super(message, params);
    }
    
}