package com.neoflex.conveyor.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ScoringException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ScoringException(String message) {
        super(message);
    }

}
