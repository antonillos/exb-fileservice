package de.exb.interviews.sapozhko;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import io.dropwizard.configuration.ConfigurationSourceProvider;

public class CombinedSourceProvider implements ConfigurationSourceProvider {

	private static final String CLASSPATH_URL_PREFIX = "classpath:";

	@Override
	public InputStream open(final String aPath) throws IOException {
		if (aPath.startsWith(CLASSPATH_URL_PREFIX)) {
			final String url = aPath.substring(CLASSPATH_URL_PREFIX.length());
			return getClass().getClassLoader().getResourceAsStream(url.startsWith("/") ? url.substring(1) : url);
		}
		return new URL(aPath).openStream();
	}
}
