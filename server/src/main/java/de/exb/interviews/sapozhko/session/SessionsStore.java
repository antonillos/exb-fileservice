package de.exb.interviews.sapozhko.session;

import java.util.Map;

public interface SessionsStore {

    Map<Object, Session> get();

    void dispose();

}