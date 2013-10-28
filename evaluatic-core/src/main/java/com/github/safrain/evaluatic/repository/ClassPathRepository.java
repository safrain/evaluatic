package com.github.safrain.evaluatic.repository;

import com.github.safrain.evaluatic.SourceCode;
import com.github.safrain.evaluatic.exception.SourceCodeModificationFailedException;
import com.github.safrain.evaluatic.exception.SourceCodeNotFoundException;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClassPathRepository implements SourceCodeRepository, ResourceLoaderAware {
    private ResourceLoader resourceLoader;

    private String charset = "utf-8";

    private String getLocation(String sourceCodeName) {
        return String.format("classpath:%s", sourceCodeName);
    }

    @Override
    public boolean exists(String name) {
        return resourceLoader.getResource(getLocation(name)).exists();
    }

    @Override
    public SourceCode get(String name) throws SourceCodeNotFoundException {
        Resource resource = resourceLoader.getResource(getLocation(name));
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
    public List<SourceCode> list() {
        if (!(resourceLoader instanceof ResourcePatternResolver)) {
            throw new UnsupportedOperationException();
        }
        try {
            List<SourceCode> result = new ArrayList<SourceCode>();
            Resource[] resources = ((ResourcePatternResolver) resourceLoader).getResources(getLocation("*"));
            for (Resource resource : resources) {
                SourceCode sourceCode = new SourceCode();
                sourceCode.setName(resource.getURI().toString());
                sourceCode.setSource(Util.readFully(resource.getInputStream(), charset));
                result.add(sourceCode);
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

}
