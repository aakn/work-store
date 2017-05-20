package com.aakn.workstore.work.resource

import com.aakn.workstore.work.dto.WorksResponse
import com.aakn.workstore.work.query.GetWorksForNamespaceAndMakeQuery
import com.aakn.workstore.work.query.GetWorksForNamespaceMakeAndModelQuery
import com.aakn.workstore.work.query.GetWorksForNamespaceQuery
import com.fasterxml.jackson.databind.ObjectMapper
import com.squarespace.jersey2.guice.JerseyGuiceUtils
import io.dropwizard.jackson.Jackson
import io.dropwizard.testing.junit.ResourceTestRule
import org.junit.Rule
import spock.lang.Specification

import static io.dropwizard.testing.FixtureHelpers.fixture

class WorkResourceSpec extends Specification {

  private GetWorksForNamespaceQuery getWorksForNamespaceQuery = Mock()
  private GetWorksForNamespaceAndMakeQuery getWorksForNamespaceAndMakeQuery = Mock()
  private GetWorksForNamespaceMakeAndModelQuery getWorksForNamespaceMakeAndModelQuery = Mock()

  @Rule
  ResourceTestRule resources = ResourceTestRule.builder()
    .addResource(new WorkResource(getWorksForNamespaceQuery, getWorksForNamespaceAndMakeQuery, getWorksForNamespaceMakeAndModelQuery))
    .build()

  private static ObjectMapper mapper

  // fixing the service locator issue as per https://github.com/HubSpot/dropwizard-guice/issues/95#issuecomment-274851181
  def setupSpec() {
    JerseyGuiceUtils.reset()
    mapper = Jackson.newObjectMapper()
  }

  def "get works should return all works for given namespace"() {
    given:
    String namespace = "test"
    WorksResponse expected = mapper.readValue(fixture("fixtures/get_works/expected_namespace_response.json"), WorksResponse.class)
    getWorksForNamespaceQuery.apply(namespace) >> expected

    when:
    def response = resources.target("/api/works/$namespace").request().get()

    then:
    response.status == 200
    response.readEntity(WorksResponse.class) == expected
  }

  def "get works should return all works for given namespace and make"() {
    given:
    String namespace = "test"
    String make = "LEICA"
    WorksResponse expected = mapper.readValue(fixture("fixtures/get_works/expected_make_response.json"), WorksResponse.class)
    getWorksForNamespaceAndMakeQuery.apply(namespace, make) >> expected

    when:
    def response = resources.target("/api/works/$namespace/$make").request().get()

    then:
    response.status == 200
    response.readEntity(WorksResponse.class) == expected
  }

  def "get works should return all works for given namespace, make and model"() {
    given:
    String namespace = "test"
    String make = "LEICA"
    String model = "D-LUX 3"
    WorksResponse expected = mapper.readValue(fixture("fixtures/get_works/expected_model_response.json"), WorksResponse.class)
    getWorksForNamespaceMakeAndModelQuery.apply(namespace, make, model) >> expected

    when:
    def response = resources.target("/api/works/$namespace/$make/$model").request().get()

    then:
    response.status == 200
    response.readEntity(WorksResponse.class) == expected
  }
}
