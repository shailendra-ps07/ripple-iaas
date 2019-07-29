package com.ripple.iaas.application;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.ripple.iaas.application.auth.AppAuthorizer;
import com.ripple.iaas.application.auth.AppOauthAuthenticator;
import com.ripple.iaas.application.auth.AuthUser;
import com.ripple.iaas.application.modules.IaaSModule;
import com.ripple.iaas.db.dao.impl.InMemoryAuthTokenDao;
import com.ripple.iaas.exceptions.CustomExceptionMapper;
import com.ripple.iaas.resources.UserResource;
import com.ripple.iaas.resources.VMResource;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.oauth.OAuthCredentialAuthFilter;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

/**
 * @author shailendra.ps
 * @since 29/04/19.
 */
public class IaaSApplication extends Application<IaaSConfiguration> {

    private String authRealm = "fdfxcv";

    private final HibernateBundle<IaaSConfiguration> hibernateBundle = new AbstractHibernateBundle<IaaSConfiguration>() {

        @Override
        public PooledDataSourceFactory getDataSourceFactory(IaaSConfiguration iaaSConfiguration) {
            return iaaSConfiguration.getDataSourceFactory();
        }
    };

    @Override
    public void initialize(Bootstrap<IaaSConfiguration> bootstrap) {
        super.initialize(bootstrap);
        bootstrap.addBundle(hibernateBundle);
    }

    @Override
    public void run(IaaSConfiguration iaasConfiguration, Environment environment) throws Exception {
        Injector injector = Guice.createInjector(new IaaSModule(hibernateBundle, iaasConfiguration));

        InMemoryAuthTokenDao inMemoryAuthTokenDao = new InMemoryAuthTokenDao();

        //Register Auth
        environment.jersey().register(new AuthDynamicFeature(
            new OAuthCredentialAuthFilter.Builder<AuthUser>()
                    .setAuthenticator(new AppOauthAuthenticator(inMemoryAuthTokenDao))
                    .setAuthorizer(new AppAuthorizer())
                    .setPrefix("Bearer")
                    .buildAuthFilter()
        ));
        environment.jersey().register(RolesAllowedDynamicFeature.class);
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(AuthUser.class));

        // Register resources
        environment.jersey().register(injector.getInstance(VMResource.class));
        environment.jersey().register(injector.getInstance(UserResource.class));

        // Register Exception Mapper
        environment.jersey().register(new CustomExceptionMapper());
    }

}
