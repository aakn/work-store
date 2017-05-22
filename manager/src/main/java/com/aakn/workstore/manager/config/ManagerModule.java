package com.aakn.workstore.manager.config;

import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import com.aakn.workstore.common.exception.BaseExceptionMapper;

import org.hibernate.SessionFactory;

import io.dropwizard.client.JerseyClientConfiguration;

import static com.google.common.base.Preconditions.checkNotNull;

public class ManagerModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(BaseExceptionMapper.class);
  }

  @Provides
  @Singleton
  public JerseyClientConfiguration providesClientConfiguration(
      Provider<ApplicationConfiguration> configuration) {
    return configuration.get().getClientConfiguration();
  }

  @Provides
  @Singleton
  public SessionFactory sessionFactory(CustomHibernateBundle hibernate) {
    return checkNotNull(hibernate.getSessionFactory());
  }
}
