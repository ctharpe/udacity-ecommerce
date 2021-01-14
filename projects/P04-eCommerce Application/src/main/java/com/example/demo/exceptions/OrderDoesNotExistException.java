package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class OrderDoesNotExistException extends RuntimeException {
    public OrderDoesNotExistException (){ }
    public OrderDoesNotExistException (String message) {
        super(message);
    }
}