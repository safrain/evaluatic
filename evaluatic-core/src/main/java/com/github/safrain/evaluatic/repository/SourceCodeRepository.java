package com.github.safrain.evaluatic.repository;

import com.github.safrain.evaluatic.SourceCode;

import java.util.List;

/**
 * SourceCode container
 *
 * @author safrain
 */
public interface SourceCodeRepository {

    /**
     * Indicate if this repository contains source code with given name
     *
     * @param name Name of the source code
     * @return
     */
    boolean exists(String name);

    /**
     * Return source code with given name
     *
     * @param name Name of the source code
     * @return Resource with given name
     * @throws SourceCodeNotExistException If requested source code does not exist
     */
    SourceCode get(String name) throws SourceCodeNotExistException;

    /**
     * Save given source code in this respository
     *
     * @param sourceCode SourceCode to save
     */
    void create(SourceCode sourceCode) throws SourceCodeModificationFailedException;

    /**
     * Update given source code
     *
     * @param sourceCode SourceCode to update
     * @throws SourceCodeModificationFailedException
     *          If update failed
     */
    void update(SourceCode sourceCode) throws SourceCodeModificationFailedException;

    /**
     * Delete source code by name
     *
     * @param name SourceCode to delete
     * @throws SourceCodeModificationFailedException
     *          If delete failed
     */
    void delete(String name) throws SourceCodeModificationFailedException;

    /**
     * Return all source code in this Repository
     *
     * @return All source code list
     */
    List<SourceCode> list();


    /**
     * Thrown if requested source code does not exists in SourceCodeRepository
     *
     * @author safrain
     */
    public class SourceCodeNotExistException extends RuntimeException {
        public SourceCodeNotExistException() {
        }

        public SourceCodeNotExistException(String message) {
            super(message);
        }

        public SourceCodeNotExistException(String message, Throwable cause) {
            super(message, cause);
        }

        public SourceCodeNotExistException(Throwable cause) {
            super(cause);
        }
    }


    /**
     * Thrown if create/update/delete is failed
     *
     * @author safrain
     */
    public class SourceCodeModificationFailedException extends RuntimeException {
        public SourceCodeModificationFailedException() {
        }

        public SourceCodeModificationFailedException(String message) {
            super(message);
        }

        public SourceCodeModificationFailedException(String message, Throwable cause) {
            super(message, cause);
        }

        public SourceCodeModificationFailedException(Throwable cause) {
            super(cause);
        }
    }

}
