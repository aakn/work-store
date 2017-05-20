package com.aakn.workstore.work.resource

import com.aakn.workstore.work.dto.WorksRequest
import com.aakn.workstore.work.dto.WorksResponse
import com.codahale.metrics.MetricRegistry
import com.fasterxml.jackson.databind.ObjectMapper
import com.squarespace.jersey2.guice.JerseyGuiceUtils
import io.dropwizard.jackson.Jackson
import io.dropwizard.testing.junit.ResourceTestRule
import io.dropwizard.views.ViewMessageBodyWriter
import org.junit.Rule
import spock.lang.Specification

import javax.ws.rs.core.MediaType

import static io.dropwizard.testing.FixtureHelpers.fixture

class WorkViewResourceSpec extends Specification {

  @Rule
  ResourceTestRule resources = ResourceTestRule.builder()
    .addResource(new WorkViewResource())
    .addProvider(new ViewMessageBodyWriter(new MetricRegistry()))
    .build()

  private static ObjectMapper mapper

  // fixing the service locator issue as per https://github.com/HubSpot/dropwizard-guice/issues/95#issuecomment-274851181
  def setupSpec() {
    JerseyGuiceUtils.reset()
    mapper = Jackson.newObjectMapper()
  }

  def "should render the index page"() {
    when:
    def response = resources.target("/foobar/works").request().get()

    then:
    response.status == 200
  }
}
