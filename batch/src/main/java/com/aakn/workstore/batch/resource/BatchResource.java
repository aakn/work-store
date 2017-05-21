package com.aakn.workstore.batch.resource;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import com.aakn.workstore.batch.action.ProcessBatchRequestAction;
import com.aakn.workstore.batch.dto.BatchWorkRequest;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.dropwizard.hibernate.UnitOfWork;
import lombok.extern.slf4j.Slf4j;

@Path("/api/works")
@Slf4j
@Singleton
public class BatchResource {

  private final ProcessBatchRequestAction processBatchRequestAction;

  @Inject
  public BatchResource(ProcessBatchRequestAction processBatchRequestAction) {
    this.processBatchRequestAction = processBatchRequestAction;
  }

  /**
   * @responseMessage 202 accepted
   * @responseMessage 400 bad request
   */
  @SuppressWarnings("javadoc")
  @UnitOfWork
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response process(@Valid BatchWorkRequest request) {
    processBatchRequestAction.accept(request);
    return Response.accepted().build();
  }

}
