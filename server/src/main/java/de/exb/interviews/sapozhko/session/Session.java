package de.exb.interviews.sapozhko.session;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Session {

	@JsonProperty
	private String identity;

	@JsonProperty
	private String session = UUID.randomUUID().toString().substring(0, 23);

	public Session() {
		// Jackson deserialization
	}

	public static Session newInstance() {
		return new Session();
	}

	public Session setIdentity(String identity) {
		this.identity = identity;
		return this;
	}

	public String getIdentity() {
		return identity;
	}

	public Session setSession(String session) {
		this.session = session;
		return this;
	}

	public String getSession() {
		return session;
	}
}