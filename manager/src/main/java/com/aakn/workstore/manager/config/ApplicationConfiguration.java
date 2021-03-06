package com.aakn.workstore.manager.config;

import com.aakn.workstore.batch.config.SeedDataConfiguration;

import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientConfiguration;
import io.dropwizard.db.DataSourceFactory;
import lombok.Data;

@Data
public class ApplicationConfiguration extends Configuration {

  @Valid
  @NotNull
  private JerseyClientConfiguration clientConfiguration;

  @Valid
  @NotNull
  private DataSourceFactory databaseConfiguration = new DataSourceFactory();

  @Valid
  @NotNull
  private Map<String, Map<String, String>> viewConfiguration;

  @Valid
  @NotNull
  private SeedDataConfiguration seedDataConfiguration;

}
