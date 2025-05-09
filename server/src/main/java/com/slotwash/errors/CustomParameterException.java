package com.slotwash.errors;


import java.util.Arrays;

public class CustomParameterException extends RuntimeException{

    private final String message;
    private final String[] params;

    public CustomParameterException(String message, String... params){
        super(message);
        this.message = message;
        this.params = params;
    }

    public ParameterizedErrorVM getErrorVM(){
        return new ParameterizedErrorVM(message, params);
    }

    @Override
    public String toString(){
        return "CustomParameterException [message ="+ message + ", params ="+ Arrays.toString(params)+ "]";
    }
    
}

