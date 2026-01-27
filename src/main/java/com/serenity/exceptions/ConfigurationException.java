package com.serenity.exceptions;

/**
 * Exception thrown when configuration reading fails.
 */
public class ConfigurationException extends FrameworkException {

    private static final long serialVersionUID = 1L;

    public ConfigurationException(String message) {
        super(message);
    }

    public ConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigurationException(Throwable cause) {
        super(cause);
    }
}
