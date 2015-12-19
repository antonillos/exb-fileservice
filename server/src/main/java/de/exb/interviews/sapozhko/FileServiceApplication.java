package de.exb.interviews.sapozhko;

import de.exb.interviews.sapozhko.resources.FileServiceResource;
import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class FileServiceApplication extends Application<FileServiceConfiguration> {

	public static void main(final String[] aArgs) throws Exception {
		new FileServiceApplication().run(new String[] { "server", "classpath:server.yml" });
	}

	@Override
	public void initialize(final Bootstrap<FileServiceConfiguration> aBootstrap) {
		aBootstrap.setConfigurationSourceProvider(new SubstitutingSourceProvider(
				new CombinedSourceProvider(), new EnvironmentVariableSubstitutor()));
	}

	@Override
	public void run(final FileServiceConfiguration aConfiguration, final Environment aEnvironment) {
		aEnvironment.jersey().register(new FileServiceResource());
	}
}
