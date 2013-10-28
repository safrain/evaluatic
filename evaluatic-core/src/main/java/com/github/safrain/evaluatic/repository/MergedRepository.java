package com.github.safrain.evaluatic.repository;

import com.github.safrain.evaluatic.SourceCode;
import com.github.safrain.evaluatic.exception.SourceCodeModificationFailedException;
import com.github.safrain.evaluatic.exception.SourceCodeNotFoundException;

import java.util.List;

public class MergedRepository implements SourceCodeRepository {
    private List<SourceCodeRepository> repositories;

    @Override
    public boolean exists(String name) {
        for (SourceCodeRepository repository : repositories) {
            if (repository.exists(name)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public SourceCode get(String name) throws SourceCodeNotFoundException {
        for (SourceCodeRepository repository : repositories) {
            if (repository.exists(name)) {
                return repository.get(name);
            }
        }
        throw new SourceCodeNotFoundException(name);
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
        throw new UnsupportedOperationException();
    }

    public List<SourceCodeRepository> getRepositories() {
        return repositories;
    }

    public void setRepositories(List<SourceCodeRepository> repositories) {
        this.repositories = repositories;
    }
}
