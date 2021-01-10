package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
public class UserNotCreatedException extends RuntimeException {
    public UserNotCreatedException(){ }
    public UserNotCreatedException(String message) {
        super(message);
    }
}
