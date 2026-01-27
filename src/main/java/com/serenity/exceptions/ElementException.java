package com.serenity.exceptions;

/**
 * Exception thrown when element interactions fail.
 */
public class ElementException extends FrameworkException {

    private static final long serialVersionUID = 1L;

    public ElementException(String message) {
        super(message);
    }

    public ElementException(String message, Throwable cause) {
        super(message, cause);
    }

    public ElementException(Throwable cause) {
        super(cause);
    }
}
