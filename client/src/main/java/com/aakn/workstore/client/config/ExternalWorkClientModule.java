package com.aakn.workstore.client.config;

import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import com.aakn.workstore.client.ExternalWorkClient;
import com.aakn.workstore.client.internal.HystrixExternalWorkClient;

import org.glassfish.jersey.logging.LoggingFeature;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.client.Client;

import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.client.JerseyClientConfiguration;
import io.dropwizard.setup.Environment;

public class ExternalWorkClientModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(ExternalWorkClient.class).to(HystrixExternalWorkClient.class);
  }

  @Provides
  @Singleton
  public Client providesClient(Provider<JerseyClientConfiguration> clientConfiguration,
                               Provider<Environment> environment) {
    Client client = new JerseyClientBuilder(environment.get())
        .using(clientConfiguration.get())
        .build("client");

    client.register(new LoggingFeature(Logger.getLogger(LoggingFeature.class.getName()),
                                       Level.INFO, LoggingFeature.Verbosity.PAYLOAD_TEXT,
                                       5 * 1024));
    return client;
  }
}
