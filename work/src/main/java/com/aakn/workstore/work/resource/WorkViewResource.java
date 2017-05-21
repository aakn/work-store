package com.aakn.workstore.work.resource;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import com.aakn.workstore.work.dto.WorksRequest;
import com.aakn.workstore.work.query.GetMakeNamesQuery;
import com.aakn.workstore.work.query.GetWorksQuery;
import com.aakn.workstore.work.view.IndexView;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.dropwizard.hibernate.UnitOfWork;
import lombok.extern.slf4j.Slf4j;


@Path("/{namespace}/works")
@Slf4j
@Singleton
@Produces(MediaType.TEXT_HTML)
public class WorkViewResource {

  private final GetWorksQuery getWorksQuery;
  private final GetMakeNamesQuery getMakeNamesQuery;

  @Inject
  public WorkViewResource(GetWorksQuery getWorksQuery, GetMakeNamesQuery getMakeNamesQuery) {
    this.getWorksQuery = getWorksQuery;
    this.getMakeNamesQuery = getMakeNamesQuery;
  }

  @GET
  @UnitOfWork
  public IndexView getIndex(@PathParam("namespace") String namespace) {
    IndexView view = new IndexView();
    view.setNamespace(namespace);
    view.setWorks(getWorksQuery.apply(new WorksRequest().namespace(namespace)));
    view.setMakeNames(getMakeNamesQuery.apply(namespace));
    return view;
  }
}
