package com.serenity.exceptions;

/**
 * Exception thrown when test assertions fail.
 */
public class AssertionException extends FrameworkException {

    private static final long serialVersionUID = 1L;

    public AssertionException(String message) {
        super(message);
    }

    public AssertionException(String message, Throwable cause) {
        super(message, cause);
    }

    public AssertionException(Throwable cause) {
        super(cause);
    }
}
