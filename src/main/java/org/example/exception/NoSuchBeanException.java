package org.example.exception;

import org.example.context.ApplicationContext;

/**
 * Thrown to indicate that no bean with provided attributes is found in {@link ApplicationContext}
 */
public class NoSuchBeanException extends RuntimeException {

    public NoSuchBeanException(String message) {
        super(message);
    }
}
