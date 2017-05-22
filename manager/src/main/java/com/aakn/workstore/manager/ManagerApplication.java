package com.aakn.workstore.manager;

import com.google.inject.Stage;

import com.aakn.workstore.batch.config.BatchModule;
import com.aakn.workstore.client.config.ExternalWorkClientModule;
import com.aakn.workstore.manager.config.ApplicationConfiguration;
import com.aakn.workstore.manager.config.ManagerModule;
import com.aakn.workstore.work.config.WorkModule;
import com.hubspot.dropwizard.guice.GuiceBundle;

import org.glassfish.jersey.logging.LoggingFeature;

import java.util.Map;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.bundles.redirect.PathRedirect;
import io.dropwizard.bundles.redirect.RedirectBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import lombok.extern.slf4j.Slf4j;

import static java.util.logging.Level.INFO;
import static java.util.logging.Logger.getLogger;
import static org.glassfish.jersey.logging.LoggingFeature.Verbosity.HEADERS_ONLY;

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
            .addModule(new WorkModule())
            .addModule(new ExternalWorkClientModule())
            .setConfigClass(ApplicationConfiguration.class)
            .enableAutoConfig("com.aakn.workstore.manager.config",
                              "com.aakn.workstore.batch.config")
            .build(Stage.DEVELOPMENT);
    bootstrap.addBundle(guiceBundle);
    bootstrap.addBundle(new ViewBundle<ApplicationConfiguration>() {
      @Override
      public Map<String, Map<String, String>> getViewConfiguration(ApplicationConfiguration config) {
        return config.getViewConfiguration();
      }
    });
    bootstrap.addBundle(new AssetsBundle("/assets", "/static"));
    bootstrap.addBundle(new AssetsBundle("/apidocs", "/apidocs", "index.html", "/apidocs"));
    bootstrap.addBundle(new RedirectBundle(new PathRedirect("/", "/gallery/redbubble")));
  }

  @Override
  public void run(ApplicationConfiguration configuration, Environment environment)
      throws Exception {
    log.info("Application has started!!");
    environment.jersey().register(new LoggingFeature(getLogger(LoggingFeature.class.getName()),
                                                     INFO, HEADERS_ONLY, 1 * 1024));
  }
}
