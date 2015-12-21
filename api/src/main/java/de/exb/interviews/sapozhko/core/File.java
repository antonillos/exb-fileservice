package de.exb.interviews.sapozhko.core;

import com.fasterxml.jackson.annotation.JsonProperty;

public class File {

    @JsonProperty
    private String path;

    @JsonProperty
    private Long size;

    public File(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }
}
