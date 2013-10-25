package com.github.safrain.evaluatic.exception;

/**
 * Thrown if error occurs while script evaluation, while script compilation is success
 *
 * @author Safrain
 */
public class ScriptRuntimeException extends RuntimeException {
	public ScriptRuntimeException() {
	}

	public ScriptRuntimeException(String message) {
		super(message);
	}

	public ScriptRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public ScriptRuntimeException(Throwable cause) {
		super(cause);
	}
}
