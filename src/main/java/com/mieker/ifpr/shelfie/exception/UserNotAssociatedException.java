package com.mieker.ifpr.shelfie.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotAssociatedException extends RuntimeException {
    public UserNotAssociatedException(String message) {
        super(message);
    }
}
