package com.aakn.workstore.batch.dto;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

import lombok.Data;

@Data
public class BatchWorkRequest {

  @URL
  @NotEmpty
  private String url;

  @NotEmpty
  private String directory;
}
