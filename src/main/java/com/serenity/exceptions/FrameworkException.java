package com.serenity.exceptions;

/**
 * Base exception class for all framework-related exceptions.
 * Extends RuntimeException to avoid forced exception handling in test code.
 */
public class FrameworkException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor with message
     * @param message - exception message
     */
    public FrameworkException(String message) {
        super(message);
    }

    /**
     * Constructor with message and cause
     * @param message - exception message
     * @param cause - root cause
     */
    public FrameworkException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor with cause only
     * @param cause - root cause
     */
    public FrameworkException(Throwable cause) {
        super(cause);
    }
}
