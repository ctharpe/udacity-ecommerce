package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class PasswordTooShortException extends RuntimeException {
    public PasswordTooShortException(){ }
    public PasswordTooShortException(String message) {
        super(message);
    }
}
