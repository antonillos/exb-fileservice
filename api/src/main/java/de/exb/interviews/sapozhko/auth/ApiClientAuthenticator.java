package de.exb.interviews.sapozhko.auth;

import com.google.common.base.Optional;
import io.dropwizard.auth.AuthFilter;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;

public interface ApiClientAuthenticator<C, P extends ApiClient> extends Authenticator<C, P> {

    AuthFilter<C, P> getAuthFilter();

    @Override
    Optional<P> authenticate(final C credentials) throws AuthenticationException;
}