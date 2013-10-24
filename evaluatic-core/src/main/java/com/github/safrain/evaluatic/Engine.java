package com.github.safrain.evaluatic;


/**
 * <p>Script engine used to compile and evaluate scripts</p>
 * All scripts must be compiled and cache in this engine before evaluation
 *
 * @author safrain
 */
public interface Engine {

    /**
     * Evaluate a compiled script
     *
     * @param sourceCode script
     * @return The evaluation result
     */
    Object evaluate(SourceCode sourceCode);

}
