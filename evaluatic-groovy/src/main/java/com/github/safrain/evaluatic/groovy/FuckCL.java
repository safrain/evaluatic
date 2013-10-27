package com.github.safrain.evaluatic.groovy;

import groovy.lang.GroovyClassLoader;
import org.codehaus.groovy.control.CompilationUnit;
import org.codehaus.groovy.control.CompilerConfiguration;

import java.security.CodeSource;

/**
 * @author safrain
 */
public class FuckCL extends GroovyClassLoader {
	public FuckCL() {
	}

	public FuckCL(ClassLoader loader) {
		super(loader);
	}

	public FuckCL(GroovyClassLoader parent) {
		super(parent);
	}

	public FuckCL(ClassLoader parent, CompilerConfiguration config, boolean useConfigurationClasspath) {
		super(parent, config, useConfigurationClasspath);
	}

	public FuckCL(ClassLoader loader, CompilerConfiguration config) {
		super(loader, config);
	}

	@Override
	protected CompilationUnit createCompilationUnit(CompilerConfiguration config, CodeSource source) {
		return new FuckCU(config, source, this);
	}
}
