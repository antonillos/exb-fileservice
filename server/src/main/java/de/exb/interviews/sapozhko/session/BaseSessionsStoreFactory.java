package de.exb.interviews.sapozhko.session;

import org.glassfish.hk2.api.Factory;

public abstract class BaseSessionsStoreFactory implements Factory<SessionsStore> {

    public void dispose(SessionsStore sessionsStore) {
        sessionsStore.dispose();
    }

}