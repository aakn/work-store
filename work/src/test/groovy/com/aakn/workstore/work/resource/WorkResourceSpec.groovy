package com.aakn.workstore.work.resource

import com.aakn.workstore.work.dto.WorksRequest
import com.aakn.workstore.work.dto.WorksResponse
import com.aakn.workstore.work.query.GetWorksQuery
import com.fasterxml.jackson.databind.ObjectMapper
import com.squarespace.jersey2.guice.JerseyGuiceUtils
import io.dropwizard.jackson.Jackson
import io.dropwizard.testing.junit.ResourceTestRule
import org.junit.Rule
import spock.lang.Specification

import static io.dropwizard.testing.FixtureHelpers.fixture

class WorkResourceSpec extends Specification {

  private GetWorksQuery getWorksQuery = Mock()

  @Rule
  ResourceTestRule resources = ResourceTestRule.builder()
    .addResource(new WorkResource(getWorksQuery))
    .build()

  private static ObjectMapper mapper

  // fixing the service locator issue as per https://github.com/HubSpot/dropwizard-guice/issues/95#issuecomment-274851181
  def setupSpec() {
    JerseyGuiceUtils.reset()
    mapper = Jackson.newObjectMapper()
  }

  def "get works should return all works for given namespace"() {
    given:
    WorksRequest request = new WorksRequest()
      .namespace("test")
      .page(new WorksRequest.Page()
      .pageNumber(2)
      .pageSize(5))
    WorksResponse expected = mapper.readValue(fixture("fixtures/get_works/expected_namespace_response.json"), WorksResponse.class)
    getWorksQuery.apply(request) >> expected

    when:
    def response = resources.target("/api/works")
      .queryParam("namespace", "test")
      .queryParam("page_size", 5)
      .queryParam("page_number", 2)
      .request().get()

    then:
    response.status == 200
    response.readEntity(WorksResponse.class) == expected
  }

  def "get works should return all works for given namespace and make"() {
    given:
    WorksRequest request = new WorksRequest()
      .namespace("test")
      .make("LEICA")
      .page(new WorksRequest.Page()
      .pageNumber(2)
      .pageSize(5))
    WorksResponse expected = mapper.readValue(fixture("fixtures/get_works/expected_make_response.json"), WorksResponse.class)
    getWorksQuery.apply(request) >> expected

    when:
    def response = resources.target("/api/works")
      .queryParam("namespace", "test")
      .queryParam("make", "LEICA")
      .queryParam("page_size", 5)
      .queryParam("page_number", 2)
      .request().get()

    then:
    response.status == 200
    response.readEntity(WorksResponse.class) == expected
  }

  def "get works should return all works for given namespace, make and model"() {
    given:
    WorksRequest request = new WorksRequest()
      .namespace("test")
      .make("LEICA")
      .model("D-LUX 3")
      .page(new WorksRequest.Page()
      .pageNumber(2)
      .pageSize(5))
    WorksResponse expected = mapper.readValue(fixture("fixtures/get_works/expected_model_response.json"), WorksResponse.class)
    getWorksQuery.apply(request) >> expected

    when:
    def response = resources.target("/api/works")
      .queryParam("namespace", "test")
      .queryParam("make", "LEICA")
      .queryParam("model", "D-LUX 3")
      .queryParam("page_size", 5)
      .queryParam("page_number", 2)
      .request().get()

    then:
    response.status == 200
    response.readEntity(WorksResponse.class) == expected
  }

  def "default page number should be set"() {
    given:
    WorksRequest request = new WorksRequest()
      .namespace("test")
      .make("LEICA")
      .model("D-LUX 3")
      .page(new WorksRequest.Page()
      .pageNumber(1)
      .pageSize(10))
    WorksResponse expected = mapper.readValue(fixture("fixtures/get_works/expected_model_response.json"), WorksResponse.class)
    getWorksQuery.apply(request) >> expected

    when:
    def response = resources.target("/api/works")
      .queryParam("namespace", "test")
      .queryParam("make", "LEICA")
      .queryParam("model", "D-LUX 3")
      .request().get()

    then:
    response.status == 200
    response.readEntity(WorksResponse.class) == expected
  }

  def "should throw up if namespace is absent"() {
    given:
    WorksRequest request = new WorksRequest()
      .namespace("test")
      .make("LEICA")
      .model("D-LUX 3")
      .page(new WorksRequest.Page()
      .pageNumber(1)
      .pageSize(10))

    when:
    def response = resources.target("/api/works")
      .queryParam("make", "LEICA")
      .queryParam("model", "D-LUX 3")
      .request().get()

    then:
    response.status == 400
  }

}
