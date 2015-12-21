package de.exb.interviews.sapozhko.core;

import com.fasterxml.jackson.annotation.JsonProperty;

public class File {

	@JsonProperty
	private String name;

	@JsonProperty
	private String path;

	@JsonProperty
	private Long size = 0L;

	public File() {
		// Jackson deserialization
	}

	public static File newInstance() {
		return new File();
	}

	public String getName() {
		return name;
	}

	public File setName(String name) {
		this.name = name;
		return this;
	}

	public String getPath() {
		return path;
	}

	public File setPath(String path) {
		this.path = path;
		return this;
	}

	public Long getSize() {
		return size;
	}

	public File setSize(Long size) {
		this.size = size;
		return this;
	}
}
