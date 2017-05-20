package com.aakn.workstore.work.resource

import com.aakn.workstore.work.dto.NamesResponse
import com.aakn.workstore.work.dto.WorksRequest
import com.aakn.workstore.work.dto.WorksResponse
import com.aakn.workstore.work.query.GetMakeNamesQuery
import com.aakn.workstore.work.query.GetModelNamesQuery
import com.aakn.workstore.work.query.GetWorksQuery
import com.fasterxml.jackson.databind.ObjectMapper
import com.squarespace.jersey2.guice.JerseyGuiceUtils
import io.dropwizard.jackson.Jackson
import io.dropwizard.testing.junit.ResourceTestRule
import org.junit.Rule
import spock.lang.Specification

import static io.dropwizard.testing.FixtureHelpers.fixture

class WorkApiResourceSpec extends Specification {

  private GetWorksQuery getWorksQuery = Mock()
  private GetMakeNamesQuery getMakeNamesQuery = Mock()
  private GetModelNamesQuery getModelNamesQuery = Mock()

  @Rule
  ResourceTestRule resources = ResourceTestRule.builder()
    .addResource(new WorkApiResource(getWorksQuery, getMakeNamesQuery, getModelNamesQuery))
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

  def "get works should set a default page number and page size"() {
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

  def "get works should throw up if namespace is absent"() {
    when:
    def response = resources.target("/api/works")
      .queryParam("make", "LEICA")
      .queryParam("model", "D-LUX 3")
      .request().get()

    then:
    response.status == 400
  }

  def "get make names should return a list of names"() {
    given:
    String namespace = "test"
    getMakeNamesQuery.apply(namespace) >> new NamesResponse(names: ["LEICA", "NIKON", "CANON"])

    when:
    def response = resources.target("/api/works/make_names")
      .queryParam("namespace", "test")
      .request().get()

    then:
    response.status == 200
    response.readEntity(NamesResponse.class).names == ["LEICA", "NIKON", "CANON"]

  }

  def "get make names should throw up if namespace is absent"() {
    when:
    def response = resources.target("/api/works/make_names").request().get()

    then:
    response.status == 400
  }

  def "get model names should return a list of names"() {
    given:
    getModelNamesQuery.apply("test", "LEICA") >> new NamesResponse(names: ["D-LUX-3"])

    when:
    def response = resources.target("/api/works/model_names")
      .queryParam("namespace", "test")
      .queryParam("make", "LEICA")
      .request().get()

    then:
    response.status == 200
    response.readEntity(NamesResponse.class).names == ["D-LUX-3"]

  }

  def "get model names should throw up if namespace is absent"() {
    when:
    def response = resources.target("/api/works/model_names")
      .queryParam("make", "LEICA")
      .request().get()

    then:
    response.status == 400
  }

  def "get model names should throw up if make is absent"() {
    when:
    def response = resources.target("/api/works/model_names")
      .queryParam("namespace", "test")
      .request().get()

    then:
    response.status == 400
  }

}
