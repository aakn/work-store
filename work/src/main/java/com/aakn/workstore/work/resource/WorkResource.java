package com.aakn.workstore.work.resource;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import com.aakn.workstore.work.dto.NamesResponse;
import com.aakn.workstore.work.dto.WorksRequest;
import com.aakn.workstore.work.dto.WorksRequest.Page;
import com.aakn.workstore.work.dto.WorksResponse;
import com.aakn.workstore.work.query.GetMakeNamesQuery;
import com.aakn.workstore.work.query.GetWorksQuery;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import io.dropwizard.hibernate.UnitOfWork;
import lombok.extern.slf4j.Slf4j;

@Path("/api/works")
@Slf4j
@Singleton
public class WorkResource {

  private final GetWorksQuery getWorksQuery;
  private final GetMakeNamesQuery getMakeNamesQuery;

  @Inject
  public WorkResource(GetWorksQuery getWorksQuery, GetMakeNamesQuery getMakeNamesQuery) {
    this.getWorksQuery = getWorksQuery;
    this.getMakeNamesQuery = getMakeNamesQuery;
  }

  @UnitOfWork
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public WorksResponse getWorksForNamespaceMakeAndModel(@QueryParam("make") String make,
                                                        @QueryParam("model") String model,
                                                        @Valid @NotEmpty @QueryParam("namespace") String namespace,
                                                        @QueryParam("page_number") @DefaultValue("1") int pageNumber,
                                                        @QueryParam("page_size") @DefaultValue("10") int pageSize) {
    WorksRequest worksRequest = new WorksRequest()
        .namespace(namespace)
        .make(make)
        .model(model)
        .page(new Page()
                  .pageNumber(pageNumber)
                  .pageSize(pageSize));
    return getWorksQuery.apply(worksRequest);
  }

  @UnitOfWork
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/make_names")
  public NamesResponse getMakeNames(@Valid @NotEmpty
                                          @QueryParam("namespace") String namespace) {
    return getMakeNamesQuery.apply(namespace);
  }
}
