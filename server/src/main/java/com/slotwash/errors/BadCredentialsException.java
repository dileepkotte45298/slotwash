package com.slotwash.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class BadCredentialsException extends CustomParameterException{

    public BadCredentialsException(String  message){
        super(message);
    }

    public BadCredentialsException(String message, String[] params) {
        super(message, params);
    }
    
}
