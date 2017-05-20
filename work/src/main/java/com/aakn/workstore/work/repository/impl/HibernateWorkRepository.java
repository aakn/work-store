package com.aakn.workstore.work.repository.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import com.aakn.workstore.work.model.Work;
import com.aakn.workstore.work.repository.WorkRepository;

import org.hibernate.SessionFactory;

import java.util.List;

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

  @Override
  @SuppressWarnings("unchecked")
  public List<Work> getWorksForNamespace(String namespace) {
    return list(namedQuery("getWorksForNamespace")
                    .setParameter("namespace", namespace));
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<Work> getWorksForNamespaceAndMake(String namespace, String make) {
    return list(namedQuery("getWorksForNamespaceAndMake")
                    .setParameter("make", make)
                    .setParameter("namespace", namespace));
  }
}
