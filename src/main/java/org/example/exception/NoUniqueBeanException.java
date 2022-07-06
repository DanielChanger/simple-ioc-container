package org.example.exception;

import org.example.context.ApplicationContext;

/**
 * Thrown to indicate that more than one bean with the same type, but different names
 * are found in the {@link ApplicationContext}
 */
public class NoUniqueBeanException extends RuntimeException {

    public NoUniqueBeanException(String message) {
        super(message);
    }
}
