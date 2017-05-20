package com.aakn.workstore.manager.config;

import com.google.inject.Singleton;

import org.hibernate.cfg.Configuration;

import io.dropwizard.ConfiguredBundle;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.ScanningHibernateBundle;
import io.dropwizard.hibernate.SessionFactoryFactory;

@Singleton
public class CustomHibernateBundle extends ScanningHibernateBundle<ApplicationConfiguration>
    implements ConfiguredBundle<ApplicationConfiguration> {

  public CustomHibernateBundle() {
    super("com.aakn.workstore", new SessionFactoryFactory());
  }

  @Override
  public DataSourceFactory getDataSourceFactory(ApplicationConfiguration configuration) {
    return configuration.getDatabaseConfiguration();
  }

  @Override
  protected void configure(Configuration configuration) {
    configuration.addResource("queries.hbm.xml");
  }
}