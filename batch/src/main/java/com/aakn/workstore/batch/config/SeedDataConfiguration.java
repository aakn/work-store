package com.aakn.workstore.batch.config;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

import lombok.Data;

@Data
public class SeedDataConfiguration {

  @NotEmpty
  @URL
  private String url;

  @NotEmpty
  private String namespace;

  private boolean enabled = true;
}
