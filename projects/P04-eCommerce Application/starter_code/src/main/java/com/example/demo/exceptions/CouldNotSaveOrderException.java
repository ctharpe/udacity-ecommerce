package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
public class CouldNotSaveOrderException extends RuntimeException {
    public CouldNotSaveOrderException(){ }
    public CouldNotSaveOrderException(String message) {
        super(message);
    }
}