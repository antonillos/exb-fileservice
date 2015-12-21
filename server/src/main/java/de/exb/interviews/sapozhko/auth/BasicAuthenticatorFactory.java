package de.exb.interviews.sapozhko.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.File;

public class BasicAuthenticatorFactory implements ApiClientAuthenticatorFactory<BasicAuthenticator> {

    @JsonProperty("properties")
    private File authProperties;

    @Override
    public BasicAuthenticator initialize() {
        return new BasicAuthenticator(authProperties);
    }

    public File getAuthProperties() {
        return authProperties;
    }
}