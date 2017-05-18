package com.aakn.workstore.manager;

import com.aakn.workstore.manager.config.ApplicationConfiguration;
import com.aakn.workstore.manager.config.ManagerModule;
import com.hubspot.dropwizard.guice.GuiceBundle;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ManagerApplication extends Application<ApplicationConfiguration> {

  public static void main(String[] args) throws Exception {
    new ManagerApplication().run(args);
  }

  @Override
  public String getName() {
    return "work-store";
  }

  @Override
  public void initialize(Bootstrap<ApplicationConfiguration> bootstrap) {
    GuiceBundle<ApplicationConfiguration> guiceBundle =
        GuiceBundle.<ApplicationConfiguration>newBuilder()
            .addModule(new ManagerModule())
            .setConfigClass(ApplicationConfiguration.class)
            .build();
    bootstrap.addBundle(guiceBundle);
  }

  @Override
  public void run(ApplicationConfiguration configuration, Environment environment)
      throws Exception {
    log.info("Application has started!!");
  }
}