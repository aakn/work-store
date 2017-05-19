package com.aakn.workstore.client.internal;

import com.google.inject.Inject;

import com.aakn.workstore.client.dto.Works;
import com.aakn.workstore.common.BaseClient;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBElement;

import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GetExternalWorkCommand extends HystrixCommand<Works> implements BaseClient {

  private final static String GROUP_KEY = "external-work-client";
  private final Client client;

  @lombok.Setter
  @Accessors(fluent = true)
  private URI uri;

  @Inject
  public GetExternalWorkCommand(Client client) {
    super(HystrixCommandGroupKey.Factory.asKey(GROUP_KEY));
    this.client = client;
  }

  @Override
  protected Works run() throws Exception {
    Response response = client.target(uri)
        .request()
        .accept(MediaType.APPLICATION_XML_TYPE)
        .get();

    checkResponse(response);

    JAXBElement<Works> worksElement = response.readEntity(new GenericType<JAXBElement<Works>>() {
    });
    return worksElement.getValue();
  }
}
