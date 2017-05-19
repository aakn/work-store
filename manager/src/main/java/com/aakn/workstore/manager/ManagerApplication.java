package com.aakn.workstore.manager;

import com.google.inject.Stage;

import com.aakn.workstore.batch.config.BatchModule;
import com.aakn.workstore.client.config.ExternalWorkClientModule;
import com.aakn.workstore.manager.config.ApplicationConfiguration;
import com.aakn.workstore.manager.config.ManagerModule;
import com.hubspot.dropwizard.guice.GuiceBundle;

import org.glassfish.jersey.logging.LoggingFeature;

import java.util.logging.Level;
import java.util.logging.Logger;

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
            .addModule(new BatchModule())
            .addModule(new ExternalWorkClientModule())
            .setConfigClass(ApplicationConfiguration.class)
            .build(Stage.DEVELOPMENT);
    bootstrap.addBundle(guiceBundle);
  }

  @Override
  public void run(ApplicationConfiguration configuration, Environment environment)
      throws Exception {
    log.info("Application has started!!");
    environment.jersey().register(new LoggingFeature(Logger.getLogger(LoggingFeature.class.getName()),
                                                     Level.INFO, LoggingFeature.Verbosity.PAYLOAD_ANY,
                                                     30 * 1024));
  }
}
