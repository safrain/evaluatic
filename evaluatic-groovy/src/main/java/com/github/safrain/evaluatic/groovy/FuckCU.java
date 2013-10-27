package com.github.safrain.evaluatic.groovy;

import groovy.lang.GroovyClassLoader;
import org.codehaus.groovy.ast.CompileUnit;
import org.codehaus.groovy.control.CompilationUnit;
import org.codehaus.groovy.control.CompilerConfiguration;

import java.security.CodeSource;

/**
 * @author safrain
 */
public class FuckCU extends CompilationUnit {
	public FuckCU() {
	}

	public FuckCU(GroovyClassLoader loader) {
		this(null, null, loader);
	}

	public FuckCU(CompilerConfiguration configuration) {
		this(configuration, null, null);
	}

	public FuckCU(CompilerConfiguration configuration, CodeSource security, GroovyClassLoader loader) {
		this(configuration, security, loader, null);
	}

	public FuckCU(CompilerConfiguration configuration, CodeSource security, GroovyClassLoader loader, GroovyClassLoader transformLoader) {
		super(configuration, security, loader, transformLoader);
		this.ast = new FuckCUnit(this.classLoader, security, this.configuration);

	}
}
