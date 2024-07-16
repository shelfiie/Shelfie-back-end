package com.mieker.ifpr.shelfie.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ExceededPageLimitException extends RuntimeException {
    public ExceededPageLimitException(String message) {
        super(message);
    }
}
