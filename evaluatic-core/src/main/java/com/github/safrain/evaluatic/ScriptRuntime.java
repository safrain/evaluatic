package com.github.safrain.evaluatic;

import com.github.safrain.evaluatic.repository.SourceCodeRepository;
import com.github.safrain.evaluatic.support.RuntimeSupport;

import java.util.HashMap;

/**
 * @author Safrain
 */
public class ScriptRuntime {
    private SourceCodeRepository sourceCodeRepository;
    private EngineFactory engineFactory;
    /**
     * Script with this name will be evaluated just after engine creation,to do some prepare works
     */
    private String loaderName = "loader.groovy";

    /**
     * Evaluate script with given name using a new script engine
     *
     * @param resourceName Resource to be evaluated
     * @return Evaluation result
     */
    public Object run(String resourceName) {
        Engine engine = engineFactory.createEngine();
        // Add predefined vars into thread local
        RuntimeSupport.sourceNameThreadLocal.set(resourceName);
        RuntimeSupport.scriptRuntimeThreadLocal.set(this);
        RuntimeSupport.engineThreadLocal.set(engine);
        RuntimeSupport.configThreadLocal.set(new HashMap<String, Object>());
        //Evaluate loader
        SourceCode loader = sourceCodeRepository.get(loaderName);
        return engine.evaluate(loader);
    }

    public EngineFactory getEngineFactory() {
        return engineFactory;
    }

    public void setEngineFactory(EngineFactory engineFactory) {
        this.engineFactory = engineFactory;
    }

    public SourceCodeRepository getSourceCodeRepository() {
        return sourceCodeRepository;
    }

    public void setSourceCodeRepository(SourceCodeRepository sourceCodeRepository) {
        this.sourceCodeRepository = sourceCodeRepository;
    }

    public String getLoaderName() {
        return loaderName;
    }

    public void setLoaderName(String loaderName) {
        this.loaderName = loaderName;
    }
}
