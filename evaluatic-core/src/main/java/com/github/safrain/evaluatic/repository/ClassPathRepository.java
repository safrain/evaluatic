package com.github.safrain.evaluatic.repository;

import com.github.safrain.evaluatic.SourceCode;
import com.github.safrain.evaluatic.exception.SourceCodeModificationFailedException;
import com.github.safrain.evaluatic.exception.SourceCodeNotFoundException;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ClassPathRepository implements SourceCodeRepository, ResourceLoaderAware {
	private ResourceLoader resourceLoader;

	private List<String> includePackages;

	private static final List<String> DEFAULT_PACKAGES = Arrays.asList("com/github/safrain/evaluatic");

	private String charset = "utf-8";


	@Override
	public boolean exists(String name) {
		return resourceLoader.getResource("classpath:" + name).exists();
	}

	@Override
	public SourceCode get(String name) throws SourceCodeNotFoundException {
		Resource resource = resourceLoader.getResource("classpath:" + name);
		if (!resource.exists()) {
			throw new SourceCodeNotFoundException(name);
		}
		try {
			String content = Util.readFully(resource.getInputStream(), charset);
			SourceCode sourceCode = new SourceCode();
			sourceCode.setSource(content);
			sourceCode.setName(name);
			return sourceCode;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void create(SourceCode sourceCode) throws SourceCodeModificationFailedException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void update(SourceCode sourceCode) throws SourceCodeModificationFailedException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void delete(String name) throws SourceCodeModificationFailedException {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<String> list() {
		if (!(resourceLoader instanceof ResourcePatternResolver)) {
			throw new UnsupportedOperationException();
		}
		List<String> result = new ArrayList<String>();
		try {
			List<Resource> resources = new ArrayList<Resource>();
			for (String p : DEFAULT_PACKAGES) {
				Collections.addAll(resources, ((ResourcePatternResolver) resourceLoader).getResources("classpath*:" + p + "/**"));
			}
			if (includePackages != null) {
				for (String p : includePackages) {
					Collections.addAll(resources, ((ResourcePatternResolver) resourceLoader).getResources("classpath*:" + p + "/**"));
				}
			}

			Resource[] fileRoot = ((ResourcePatternResolver) resourceLoader).getResources("classpath*:");

			for (Resource resource : resources) {
				String path = resource.getURL().getPath();
				if (!resource.exists() || !resource.isReadable() || path.endsWith("/")) {
					continue;
				}
				String name = null;
				URL url = resource.getURL();
				if ("jar".equals(url.getProtocol())) {
					name = path.substring(path.indexOf('!') + 1);
				} else if ("file".equals(url.getProtocol())) {
					for (Resource root : fileRoot) {
						if (path.startsWith(root.getURL().getPath())) {
							name = path.substring(root.getURL().getPath().length());
							break;
						}
					}
					if (name == null) {
						throw new IllegalStateException();
					}
				} else {
					throw new IllegalArgumentException();
				}
				result.add(name);
			}
			return result;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public List<String> getIncludePackages() {
		return includePackages;
	}

	public void setIncludePackages(List<String> includePackages) {
		this.includePackages = includePackages;
	}

}
