package com.github.safrain.evaluatic.exception;

/**
 * Throw when script compilation failed
 *
 * @author Safrain
 */
public class CompileException extends RuntimeException {
	public CompileException() {
	}

	public CompileException(String message) {
		super(message);
	}

	public CompileException(String message, Throwable cause) {
		super(message, cause);
	}

	public CompileException(Throwable cause) {
		super(cause);
	}
}
