package com.aakn.workstore.work.resource;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import com.aakn.workstore.work.dto.WorksResponse;
import com.aakn.workstore.work.query.GetWorksForNamespaceAndMakeQuery;
import com.aakn.workstore.work.query.GetWorksForNamespaceQuery;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.dropwizard.hibernate.UnitOfWork;
import lombok.extern.slf4j.Slf4j;

@Path("/api/works")
@Slf4j
@Singleton
public class WorkResource {

  private final GetWorksForNamespaceQuery getWorksForNamespaceQuery;
  private final GetWorksForNamespaceAndMakeQuery getWorksForNamespaceAndMakeQuery;

  @Inject
  public WorkResource(GetWorksForNamespaceQuery getWorksForNamespaceQuery,
                      GetWorksForNamespaceAndMakeQuery getWorksForNamespaceAndMakeQuery) {
    this.getWorksForNamespaceQuery = getWorksForNamespaceQuery;
    this.getWorksForNamespaceAndMakeQuery = getWorksForNamespaceAndMakeQuery;
  }

  @UnitOfWork
  @GET
  @Path("/{namespace}")
  @Produces(MediaType.APPLICATION_JSON)
  public WorksResponse getWorksForNamespace(@PathParam("namespace") String namespace) {
    return getWorksForNamespaceQuery.apply(namespace);
  }

  @UnitOfWork
  @GET
  @Path("/{namespace}/{make}")
  @Produces(MediaType.APPLICATION_JSON)
  public WorksResponse getWorksForNamespaceAndMake(@PathParam("namespace") String namespace,
                                                   @PathParam("make") String make) {
    return getWorksForNamespaceAndMakeQuery.apply(namespace, make);
  }

}
