package com.github.safrain.evaluatic.groovy;

import com.github.safrain.evaluatic.SourceCode;
import com.github.safrain.evaluatic.support.RuntimeSupport;
import groovy.lang.GroovyClassLoader;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.CompileUnit;
import org.codehaus.groovy.control.CompilationUnit;
import org.codehaus.groovy.control.CompilerConfiguration;

import java.security.CodeSource;

/**
 * @author safrain
 */
public class FuckCUnit extends CompileUnit {
	public FuckCUnit(GroovyClassLoader classLoader, CompilerConfiguration config) {
		super(classLoader, config);
	}

	public FuckCUnit(GroovyClassLoader classLoader, CodeSource codeSource, CompilerConfiguration config) {
		super(classLoader, codeSource, config);
	}

	@Override
	public ClassNode getClass(String name) {
		if ("com.github.safrain.evaluatic.groovy.Beans".equals(name)) {

			SourceCode c = RuntimeSupport.getScriptRuntime().getSourceCodeRepository().get("com/github/safrain/evaluatic/groovy/beans.groovy");
			GroovyEngineFactory f = ((GroovyEngineFactory.GroovyEngine) RuntimeSupport.getEngine()).getFactory();
			Class<?> clazz = f.compile(c);
			return new ClassNode(clazz);
		}
		return super.getClass(name);
	}
}
