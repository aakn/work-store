package com.aakn.workstore.work.resource

import com.aakn.workstore.work.dto.NamesResponse
import com.aakn.workstore.work.dto.WorksRequest
import com.aakn.workstore.work.dto.WorksResponse
import com.aakn.workstore.work.query.GetMakeNamesQuery
import com.aakn.workstore.work.query.GetModelNamesQuery
import com.aakn.workstore.work.query.GetWorksQuery
import com.codahale.metrics.MetricRegistry
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.collect.ImmutableList
import com.squarespace.jersey2.guice.JerseyGuiceUtils
import io.dropwizard.jackson.Jackson
import io.dropwizard.testing.junit.ResourceTestRule
import io.dropwizard.views.ViewMessageBodyWriter
import io.dropwizard.views.ViewRenderExceptionMapper
import io.dropwizard.views.freemarker.FreemarkerViewRenderer
import org.junit.Rule
import spock.lang.Specification

import static io.dropwizard.testing.FixtureHelpers.fixture

class WorkViewResourceSpec extends Specification {

  private GetWorksQuery getWorksQuery = Mock()
  private GetMakeNamesQuery getMakeNamesQuery = Mock()
  private GetModelNamesQuery getModelNamesQuery = Mock()

  @Rule
  ResourceTestRule resources = ResourceTestRule.builder()
    .addResource(new WorkViewResource(getWorksQuery, getMakeNamesQuery, getModelNamesQuery))
    .addProvider(new ViewMessageBodyWriter(new MetricRegistry(), ImmutableList.of(buildRenderer())))
    .addProvider(new ViewRenderExceptionMapper())
    .build()
  private static ObjectMapper mapper

  // fixing the service locator issue as per https://github.com/HubSpot/dropwizard-guice/issues/95#issuecomment-274851181
  def setupSpec() {
    JerseyGuiceUtils.reset()
    mapper = Jackson.newObjectMapper()
  }

  def "should render the index page"() {
    given:
    WorksRequest request = new WorksRequest()
      .namespace("test")
    WorksResponse expected = mapper.readValue(fixture("fixtures/get_works/expected_namespace_response.json"), WorksResponse.class)
    getWorksQuery.apply(request) >> expected
    getMakeNamesQuery.apply("test") >> new NamesResponse(names: ["LEICA", "NIKON", "CANON"])

    when:
    def response = resources.target("/test/works").request().get()

    then:
    response.status == 200
  }

  def "should render the make page"() {
    given:
    WorksRequest request = new WorksRequest()
      .namespace("test")
      .make("LEICA")
    WorksResponse expected = mapper.readValue(fixture("fixtures/get_works/expected_make_response.json"), WorksResponse.class)
    getWorksQuery.apply(request) >> expected
    getModelNamesQuery.apply("test", "LEICA") >> new NamesResponse(names: ["D-LUX 3", "D-LUX 4"])

    when:
    def response = resources.target("/test/works/make/LEICA").request().get()

    then:
    response.status == 200
  }

  def "should render the model page"() {
    given:
    WorksRequest request = new WorksRequest()
      .namespace("test")
      .make("LEICA")
      .model("D-LUX 3")
      .page(new WorksRequest.Page()
      .pageSize(100))
    WorksResponse expected = mapper.readValue(fixture("fixtures/get_works/expected_model_response.json"), WorksResponse.class)
    getWorksQuery.apply(request) >> expected
    getModelNamesQuery.apply("test", "LEICA") >> new NamesResponse(names: ["D-LUX 3", "D-LUX 4"])

    when:
    def response = resources.target("/test/works/make/LEICA/model/D-LUX 3").request().get()

    then:
    response.status == 200
  }

  // Using this to catch any freemarker errors during the test
  private static FreemarkerViewRenderer buildRenderer() {
    def renderer = new FreemarkerViewRenderer()
    renderer.configure([template_exception_handler: "rethrow"])
    return renderer
  }
}
