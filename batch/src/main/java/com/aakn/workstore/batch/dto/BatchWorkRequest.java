package com.aakn.workstore.batch.dto;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import lombok.Data;

@Data
public class BatchWorkRequest {

  @NotEmpty
  @URL
  private String url;

  @NotEmpty
  private String directory;

  public URI getUrl() {
    return UriBuilder.fromUri(url).build();
  }
}
