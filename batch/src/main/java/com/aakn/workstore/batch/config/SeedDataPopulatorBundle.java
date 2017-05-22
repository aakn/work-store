package com.aakn.workstore.batch.config;

import com.google.inject.Inject;
import com.google.inject.Provider;

import com.aakn.workstore.batch.action.SeedDataPopulatorAction;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.context.internal.ManagedSessionContext;

import io.dropwizard.lifecycle.Managed;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SeedDataPopulatorBundle implements Managed {

  private final Provider<SeedDataConfiguration> seedDataConfiguration;
  private final Provider<SessionFactory> sessionFactory;
  private final Provider<SeedDataPopulatorAction> seedDataPopulatorAction;

  @Inject
  public SeedDataPopulatorBundle(Provider<SeedDataConfiguration> seedDataConfiguration,
                                 Provider<SessionFactory> sessionFactory,
                                 Provider<SeedDataPopulatorAction> seedDataPopulatorAction) {
    this.seedDataConfiguration = seedDataConfiguration;
    this.sessionFactory = sessionFactory;
    this.seedDataPopulatorAction = seedDataPopulatorAction;
  }

  @Override
  public void start() throws Exception {
    if (!seedDataConfiguration.get().isEnabled()) {
      log.info("seeding has been disabled");
      return;
    }

    Session session = null;
    try {
      session = sessionFactory.get().openSession();
      ManagedSessionContext.bind(session);
      Transaction transaction = session.beginTransaction();
      try {
        seedDataPopulatorAction.get().accept();
        transaction.commit();
      } catch (Exception e) {
        transaction.rollback();
        log.error("Exception occurred while executing transaction", e);
      }
    } finally {
      if (session != null) {
        session.close();
      }
      ManagedSessionContext.unbind(sessionFactory.get());
    }
  }

  @Override
  public void stop() throws Exception {

  }
}
