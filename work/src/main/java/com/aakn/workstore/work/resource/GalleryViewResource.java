package com.aakn.workstore.work.resource;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import com.aakn.workstore.work.dto.WorksRequest;
import com.aakn.workstore.work.query.GetMakeNamesQuery;
import com.aakn.workstore.work.query.GetModelNamesQuery;
import com.aakn.workstore.work.query.GetWorksQuery;
import com.aakn.workstore.work.view.IndexView;
import com.aakn.workstore.work.view.MakeView;
import com.aakn.workstore.work.view.ModelView;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import io.dropwizard.hibernate.UnitOfWork;
import lombok.extern.slf4j.Slf4j;


@Path("/gallery/{namespace}")
@Slf4j
@Singleton
@Produces(MediaType.TEXT_HTML)
public class GalleryViewResource {

  private final GetWorksQuery getWorksQuery;
  private final GetMakeNamesQuery getMakeNamesQuery;
  private final GetModelNamesQuery getModelNamesQuery;

  @Inject
  public GalleryViewResource(GetWorksQuery getWorksQuery, GetMakeNamesQuery getMakeNamesQuery,
                             GetModelNamesQuery getModelNamesQuery) {
    this.getWorksQuery = getWorksQuery;
    this.getMakeNamesQuery = getMakeNamesQuery;
    this.getModelNamesQuery = getModelNamesQuery;
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

  @GET
  @Path("/make/{make}")
  @UnitOfWork
  public MakeView getMakePage(@PathParam("namespace") String namespace,
                              @PathParam("make") String make) {
    MakeView view = new MakeView();
    view.setMake(make);
    view.setNamespace(namespace);
    view.setWorks(getWorksQuery.apply(new WorksRequest()
                                          .namespace(namespace)
                                          .make(make)));
    view.setModelNames(getModelNamesQuery.apply(namespace, make));
    return view;
  }

  @GET
  @Path("/model/{model}")
  @UnitOfWork
  public ModelView getModelPage(@PathParam("namespace") String namespace,
                                @Valid @NotEmpty @QueryParam("make") String make,
                                @PathParam("model") String model) {
    ModelView view = new ModelView();
    view.setNamespace(namespace);
    view.setMake(make);
    view.setModel(model);
    view.setWorks(getWorksQuery.apply(new WorksRequest()
                                          .namespace(namespace)
                                          .model(model)
                                          .make(make)
                                          .page(new WorksRequest.Page()
                                                    .pageSize(100))));
    view.setModelNames(getModelNamesQuery.apply(namespace, make));
    return view;
  }
}
