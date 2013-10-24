package com.github.safrain.evaluatic;

/**
 * Script source code used by framework and user applications
 *
 * @author Safrain
 */
public class SourceCode {
    private String name;
    private String source;
    private long lastModified;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public long getLastModified() {
        return lastModified;
    }

    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }
}
