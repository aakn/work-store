package com.aakn.workstore.work.resource;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import com.aakn.workstore.work.view.IndexView;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import lombok.extern.slf4j.Slf4j;


@Path("/{namespace}/works")
@Slf4j
@Singleton
@Produces(MediaType.TEXT_HTML)
public class WorkViewResource {

  @Inject
  public WorkViewResource() {
  }

  @GET
  public IndexView getIndex(@PathParam("namespace") String namespace) {
    IndexView view = new IndexView();
    view.setNamespace(namespace);
    return view;
  }
}
