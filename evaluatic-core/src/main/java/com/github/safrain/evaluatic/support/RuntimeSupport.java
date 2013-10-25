package com.github.safrain.evaluatic.support;

import com.github.safrain.evaluatic.Engine;
import com.github.safrain.evaluatic.ScriptRuntime;
import com.github.safrain.evaluatic.SourceCode;
import com.github.safrain.evaluatic.repository.SourceCodeRepository;

import java.util.Map;

public class RuntimeSupport {
    public static final InheritableThreadLocal<ScriptRuntime> scriptRuntimeThreadLocal = new InheritableThreadLocal<ScriptRuntime>();
    public static final InheritableThreadLocal<Engine> engineThreadLocal = new InheritableThreadLocal<Engine>();
    public static final InheritableThreadLocal<String> sourceNameThreadLocal = new InheritableThreadLocal<String>();
    public static final InheritableThreadLocal<Map<String, Object>> configThreadLocal = new InheritableThreadLocal<Map<String, Object>>();


    public static SourceCode load(String name) {
        SourceCodeRepository repo = getScriptRuntime().getSourceCodeRepository();
        return repo.get(name);
    }

    public static String loadString(String name) {
        return load(name).getSource();
    }

    public static Object eval(String name) {
        return getEngine().evaluate(load(name));
    }

    public static ScriptRuntime getScriptRuntime() {
        return scriptRuntimeThreadLocal.get();
    }

    public static Engine getEngine() {
        return engineThreadLocal.get();
    }

    public static String getSourceName() {
        return sourceNameThreadLocal.get();
    }

    public static Map<String, Object> getConfig() {
        return configThreadLocal.get();
    }
}
