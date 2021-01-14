package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NO_CONTENT)
public class EmptyOrderException extends RuntimeException {
    EmptyOrderException() {};
    public EmptyOrderException(String message) {
        super(message);
    }
}
