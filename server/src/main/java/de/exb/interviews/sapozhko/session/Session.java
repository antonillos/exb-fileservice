package de.exb.interviews.sapozhko.session;

import java.util.UUID;

public class Session {

    private final String identity;
    private final String session;

    public Session(String username) {
        this.identity = username;
        this.session = UUID.randomUUID().toString().substring(0, 23);
    }

    public Session(String identity, String session) {
        this.identity = identity;
        this.session = session;
    }

    public String getIdentity() {
        return identity;
    }

    public String getSession() {
        return session;
    }
}