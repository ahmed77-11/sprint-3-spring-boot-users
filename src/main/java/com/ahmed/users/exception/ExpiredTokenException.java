package com.ahmed.users.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ExpiredTokenException extends RuntimeException{
    private String message;
    public ExpiredTokenException(String message){
        super(message);
    }
}
