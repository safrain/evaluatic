package com.github.safrain.evaluatic.repository;


import com.github.safrain.evaluatic.SourceCode;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileSystemRepository implements SourceCodeRepository {

    private File baseDirectory;
    private String charset;

    private SourceCode fileToSourceCode(File file) {
        FileInputStream fis;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            return null;
        }
        byte[] resourceValue;
        try {
            resourceValue = Util.readFully(fis);
        } finally {
            Util.closeQuietly(fis);
        }
        String name = file.getAbsolutePath().substring(baseDirectory.getAbsolutePath().length());
        while (name.startsWith("/")) {
            name = name.substring(1);
        }

        SourceCode sourceCode = new SourceCode();
        sourceCode.setName(name);
        sourceCode.setLastModified(file.lastModified());
        try {
            sourceCode.setSource(new String(resourceValue, charset));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return sourceCode;
    }

    @Override
    public boolean exists(String name) {
        return new File(baseDirectory, name).isFile();
    }

    @Override
    public SourceCode get(String name) throws SourceCodeNotExistException {
        File file = new File(baseDirectory, name);
        if (!file.isFile()) {
            throw new SourceCodeNotExistException(name);
        }
        SourceCode resource = fileToSourceCode(file);
        if (resource == null) {
            throw new SourceCodeNotExistException(name);
        }
        return resource;
    }


    @Override
    public void create(SourceCode sourceCode) throws SourceCodeModificationFailedException {
        File file = new File(baseDirectory, sourceCode.getName());
        file.getParentFile().mkdirs();
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            if (sourceCode.getSource() != null) {
                fos.write(sourceCode.getSource().getBytes(charset));
            }
            fos.flush();
        } catch (Exception e) {
            throw new SourceCodeModificationFailedException(e);
        } finally {
            Util.closeQuietly(fos);
        }
    }

    @Override
    public void update(SourceCode sourceCode) throws SourceCodeModificationFailedException {
        File file = new File(baseDirectory, sourceCode.getName());
        if (!file.isFile()) {
            throw new SourceCodeNotExistException(sourceCode.getName());
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            if (sourceCode.getSource() != null) {
                fos.write(sourceCode.getSource().getBytes(charset));
            }
            fos.flush();
        } catch (Exception e) {
            throw new SourceCodeModificationFailedException(e);
        } finally {
            Util.closeQuietly(fos);
        }
    }


    @Override
    public void delete(String name) throws SourceCodeModificationFailedException {
        File file = new File(baseDirectory, name);
        if (!file.isFile()) {
            throw new SourceCodeNotExistException(name);
        }
        if (!file.delete()) {
            throw new SourceCodeModificationFailedException("Delete failed file:" + name);
        }
    }


    private void collect(File directory, final List<SourceCode> resourceList) {
        directory.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                if (file.isDirectory()) {
                    collect(file, resourceList);
                } else if (file.isFile()) {
                    resourceList.add(fileToSourceCode(file));
                }
                return true;
            }
        });
    }

    @Override
    public List<SourceCode> list() {
        List<SourceCode> resources = new ArrayList<SourceCode>();
        collect(baseDirectory, resources);
        return resources;
    }

    public File getBaseDirectory() {
        return baseDirectory;
    }

    public void setBaseDirectory(File baseDirectory) {
        this.baseDirectory = baseDirectory;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }
}
