package com.serenity.exceptions;

/**
 * Exception thrown when driver initialization or operations fail.
 */
public class DriverException extends FrameworkException {

    private static final long serialVersionUID = 1L;

    public DriverException(String message) {
        super(message);
    }

    public DriverException(String message, Throwable cause) {
        super(message, cause);
    }

    public DriverException(Throwable cause) {
        super(cause);
    }
}
