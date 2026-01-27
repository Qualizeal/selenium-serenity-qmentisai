package com.serenity.exceptions;

/**
 * Exception thrown when page load or page operations fail.
 */
public class PageException extends FrameworkException {

    private static final long serialVersionUID = 1L;

    public PageException(String message) {
        super(message);
    }

    public PageException(String message, Throwable cause) {
        super(message, cause);
    }

    public PageException(Throwable cause) {
        super(cause);
    }
}
