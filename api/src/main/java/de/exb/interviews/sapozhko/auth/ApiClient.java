package de.exb.interviews.sapozhko.auth;

import java.security.Principal;

public interface ApiClient extends Principal {

    default String getId() {
        return this.getName();
    }

}