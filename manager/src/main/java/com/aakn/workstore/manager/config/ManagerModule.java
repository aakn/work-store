package com.aakn.workstore.manager.config;

import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import io.dropwizard.client.JerseyClientConfiguration;

public class ManagerModule extends AbstractModule {

  @Override
  protected void configure() {

  }

  @Provides
  @Singleton
  public JerseyClientConfiguration providesClientConfiguration(
      Provider<ApplicationConfiguration> configuration) {
    return configuration.get().getClientConfiguration();
  }
}
