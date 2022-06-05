package com.neoflex.conveyor.Exceptions;

public class ScoringException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ScoringException() {
        super();
    }

    public ScoringException(String message, Throwable cause) {
        super(message, cause);
    }

    public ScoringException(String message) {
        super(message);
    }

}
