package de.exb.interviews.sapozhko.auth;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "factory")
public interface ApiClientAuthenticatorFactory<A extends ApiClientAuthenticator> {

    A initialize();

}