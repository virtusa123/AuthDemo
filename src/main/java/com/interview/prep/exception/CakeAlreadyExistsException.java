package com.interview.prep.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class CakeAlreadyExistsException extends Exception {
    public CakeAlreadyExistsException(String message) {
        super(message);
    }
}
