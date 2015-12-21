package de.exb.interviews.sapozhko;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.exb.interviews.sapozhko.auth.ApiClientAuthenticatorFactory;
import de.exb.interviews.sapozhko.session.SessionConfig;
import io.dropwizard.Configuration;

import javax.validation.constraints.NotNull;

public class FileServiceConfiguration extends Configuration {

    @NotNull
    @JsonProperty("authenticator")
    private ApiClientAuthenticatorFactory authenticator;

    public ApiClientAuthenticatorFactory getAuthenticator() {
        return authenticator;
    }

    @NotNull
    @JsonProperty("sessionConfig")
    private SessionConfig sessionConfig;

    public SessionConfig getSessionConfig() {
        return sessionConfig;
    }
}
