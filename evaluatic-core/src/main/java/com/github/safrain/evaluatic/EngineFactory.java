package com.github.safrain.evaluatic;

/**
 * Just an engine factory
 *
 * @author safrain
 */
public interface EngineFactory {
	/**
	 * Create script engine,each engine must have isolated script context
	 *
	 * @return The created engine
	 */
	Engine createEngine();
}
