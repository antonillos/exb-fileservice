package de.exb.interviews.sapozhko.session;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.glassfish.hk2.api.Factory;

public class SessionConfig extends Configuration {
    @JsonProperty("sessionTimeoutSecs")
    private int sessionTimeoutSecs = 15 * 60; // 15 minutes by default

    public int getSessionTimeoutSecs() {
        return sessionTimeoutSecs;
    }

    @JsonProperty("sessionsStoreFactory")
    private Class<? extends Factory<SessionsStore>> sessionsStoreFactoryClass = InMemorySessionsStoreFactory.class;

    public Class<? extends Factory<SessionsStore>> getSessionsStoreFactoryClass() {
        return sessionsStoreFactoryClass;
    }
}
