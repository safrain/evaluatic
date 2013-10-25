package com.github.safrain.evaluatic.exception;

/**
 * Thrown if create/update/delete is failed
 *
 * @author safrain
 */
public class SourceCodeModificationFailedException extends RuntimeException {

    public SourceCodeModificationFailedException(String name, Throwable cause) {
        super(String.format("SourceCode '%s' not found.", name), cause);
    }

    public SourceCodeModificationFailedException(String name) {
        super(String.format("SourceCode '%s' not found.", name));
    }
}