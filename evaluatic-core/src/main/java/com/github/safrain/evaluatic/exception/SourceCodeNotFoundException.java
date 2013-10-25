package com.github.safrain.evaluatic.exception;

/**
 * Thrown if requested resource does not exists in ResourceManager
 *
 * @author Safrain
 */
public class SourceCodeNotFoundException extends RuntimeException {
    public SourceCodeNotFoundException(String resourceName) {
        super(String.format("SourceCode '%s' not found.", resourceName));
    }
}
