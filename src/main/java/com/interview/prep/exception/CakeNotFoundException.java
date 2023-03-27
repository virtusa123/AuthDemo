package com.interview.prep.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class CakeNotFoundException extends Exception {

    public CakeNotFoundException(String message) {
        super(message);
    }
}
