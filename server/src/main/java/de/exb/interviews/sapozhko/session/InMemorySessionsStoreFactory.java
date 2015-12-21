package de.exb.interviews.sapozhko.session;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import javax.inject.Inject;
import java.util.Map;

import static java.util.concurrent.TimeUnit.SECONDS;

public class InMemorySessionsStoreFactory extends BaseSessionsStoreFactory {

    @Inject
    private SessionConfig sessionConfig;

    @Override
    public SessionsStore provide() {
        return new InMemorySessionsStore(sessionConfig);
    }

    public static class InMemorySessionsStore implements SessionsStore {

        private Cache<Object, Session> cache;

        public InMemorySessionsStore(SessionConfig sessionConfig) {
            cache = CacheBuilder.newBuilder()
                    .expireAfterWrite(sessionConfig.getSessionTimeoutSecs(), SECONDS)
                    .build();
        }

        @Override
        public Map<Object, Session> get() {
            return cache.asMap();
        }

        @Override
        public void dispose() {
            cache.cleanUp();
            cache = null;
        }

    }
}