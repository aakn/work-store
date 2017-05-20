package com.aakn.workstore.work.repository.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import com.aakn.workstore.work.dto.WorksRequest;
import com.aakn.workstore.work.model.Work;
import com.aakn.workstore.work.repository.WorkRepository;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

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
  public List<Work> getWorks(WorksRequest request) {
    Criteria criteria = criteria()
        .add(Restrictions.eq("namespace", request.namespace()))
        .add(Restrictions.isNotNull("exif.make"))
        .setMaxResults(request.page().pageSize())
        .setFirstResult(calculateFirstResultPosition(request));

    request.make().ifPresent(make -> criteria.add(Restrictions.eq("exif.make", make)));
    request.model().ifPresent(model -> criteria.add(Restrictions.eq("exif.model", model)));

    return criteria.list();
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

  @Override
  @SuppressWarnings("unchecked")
  public List<Work> getWorksForNamespaceMakeAndModel(String namespace, String make, String model) {
    return list(namedQuery("getWorksForNamespaceMakeAndModel")
                    .setParameter("make", make)
                    .setParameter("model", model)
                    .setParameter("namespace", namespace));
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<String> getUniqueMakeNames(String namespace) {
    return list(namedQuery("getUniqueMakeNames")
                    .setParameter("namespace", namespace));
  }

  private int calculateFirstResultPosition(WorksRequest request) {
    return (request.page().pageNumber() - 1) * request.page().pageSize();
  }
}
