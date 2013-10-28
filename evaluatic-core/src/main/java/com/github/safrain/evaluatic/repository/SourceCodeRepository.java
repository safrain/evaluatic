package com.github.safrain.evaluatic.repository;

import com.github.safrain.evaluatic.SourceCode;
import com.github.safrain.evaluatic.exception.SourceCodeModificationFailedException;
import com.github.safrain.evaluatic.exception.SourceCodeNotFoundException;

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
	 * @throws SourceCodeNotFoundException If requested source code does not exist
	 */
	SourceCode get(String name) throws SourceCodeNotFoundException;

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
	 * Return all source code names in this Repository
	 *
	 * @return All source code name list
	 */
	List<String> list();


}
