package com.aakn.workstore.work.repository.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import com.aakn.workstore.work.model.Work;
import com.aakn.workstore.work.repository.WorkRepository;

import org.hibernate.SessionFactory;

import io.dropwizard.hibernate.AbstractDAO;

@Singleton
public class HibernateWorkRepository extends AbstractDAO<Work> implements WorkRepository {

  @Inject
  public HibernateWorkRepository(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  @Override
  public Work persist(Work work) {
    return super.persist(work);
  }
}
