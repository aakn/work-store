package com.aakn.workstore.client.config;

import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import com.aakn.workstore.client.ExternalWorkClient;
import com.aakn.workstore.client.internal.HystrixExternalWorkClient;

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
    return new JerseyClientBuilder(environment.get())
        .using(clientConfiguration.get())
        .build("client");
  }
}
