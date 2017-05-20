package com.aakn.workstore.work.resource

import com.aakn.workstore.work.dto.WorksResponse
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

  @Rule
  ResourceTestRule resources = ResourceTestRule.builder()
    .addResource(new WorkResource(getWorksForNamespaceQuery))
    .build()

  private static ObjectMapper mapper

  // fixing the service locator issue as per https://github.com/HubSpot/dropwizard-guice/issues/95#issuecomment-274851181
  def setupSpec() {
    JerseyGuiceUtils.reset()
    mapper = Jackson.newObjectMapper()
  }

  def "get works should return serialized works"() {
    given:
    String namespace = "test"
    WorksResponse expected = mapper.readValue(fixture("fixtures/get_works/expected_response.json"), WorksResponse.class)
    getWorksForNamespaceQuery.apply(namespace) >> expected

    when:
    def response = resources.target("/api/works/$namespace").request().get()

    then:
    response.status == 200
    response.readEntity(WorksResponse.class) == expected

  }
}
