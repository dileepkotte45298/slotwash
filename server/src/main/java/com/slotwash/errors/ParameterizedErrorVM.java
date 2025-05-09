package com.slotwash.errors;

import java.io.Serializable;

public class ParameterizedErrorVM implements Serializable{

    private final String message;
    private final String[] params;

    public ParameterizedErrorVM(String message, String... params){
        this.message = message;
        this.params = params;
    }

    public String getMessage(){
        return message;
    }

    public String[] getParams(){
        return params;
    }
    
}
