package de.exb.interviews.sapozhko;

import de.exb.interviews.sapozhko.auth.ApiClient;
import de.exb.interviews.sapozhko.auth.ApiClientAuthenticator;
import de.exb.interviews.sapozhko.auth.ApiClientAuthenticatorFactory;
import de.exb.interviews.sapozhko.providers.IOExceptionMapper;
import de.exb.interviews.sapozhko.providers.RuntimeExceptionMapper;
import de.exb.interviews.sapozhko.resources.FileServiceResource;
import de.exb.interviews.sapozhko.service.FileService;
import de.exb.interviews.sapozhko.service.FileServiceImpl;
import de.exb.interviews.sapozhko.session.SessionConfig;
import de.exb.interviews.sapozhko.session.SessionsStore;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class FileServiceApplication extends Application<FileServiceConfiguration> {

    public static void main(final String... aArgs) throws Exception {
        new FileServiceApplication().run(new String[]{"server", "classpath:server.yml"});
    }

    @Override
    public void initialize(final Bootstrap<FileServiceConfiguration> aBootstrap) {
        aBootstrap.setConfigurationSourceProvider(new SubstitutingSourceProvider(
                new CombinedSourceProvider(), new EnvironmentVariableSubstitutor()));
        aBootstrap.addBundle(new ViewBundle<>());

    }

    @Override
    public void run(final FileServiceConfiguration aConfiguration, final Environment aEnvironment) {

        final ApiClientAuthenticatorFactory apiClientAuthenticatorFactory = aConfiguration.getAuthenticator();
        final ApiClientAuthenticator authenticator = apiClientAuthenticatorFactory.initialize();
        aEnvironment.jersey().register(new AuthDynamicFeature(authenticator.getAuthFilter()));
        aEnvironment.jersey().register(new AuthValueFactoryProvider.Binder<>(ApiClient.class));

        aEnvironment.jersey().register(new FileServiceResource());

        aEnvironment.jersey().register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(FileServiceImpl.class).to(FileService.class);

                bind(aConfiguration.getSessionConfig()).to(SessionConfig.class);
                bindFactory(aConfiguration.getSessionConfig().getSessionsStoreFactoryClass()).to(SessionsStore.class);
            }
        });

        aEnvironment.jersey().register(new RuntimeExceptionMapper());
        aEnvironment.jersey().register(new IOExceptionMapper());

    }
}
