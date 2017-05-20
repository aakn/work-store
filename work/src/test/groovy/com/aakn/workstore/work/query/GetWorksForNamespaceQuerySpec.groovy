package com.aakn.workstore.work.query

import com.aakn.workstore.work.dto.WorksResponse
import com.aakn.workstore.work.model.Work
import com.aakn.workstore.work.repository.WorkRepository
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import io.dropwizard.jackson.Jackson
import spock.lang.Specification

import static io.dropwizard.testing.FixtureHelpers.fixture

class GetWorksForNamespaceQuerySpec extends Specification {

  private GetWorksForNamespaceQuery query
  private ObjectMapper mapper
  private WorkRepository workRepository = Mock()

  void setup() {
    query = new GetWorksForNamespaceQuery(workRepository)
    mapper = Jackson.newObjectMapper()
  }

  def "should return works"() {
    given:
    String namespace = "test"
    WorksResponse expected = mapper.readValue(fixture("fixtures/get_works/expected_response.json"), WorksResponse.class)
    List<Work> workEntities = mapper.readValue(fixture("fixtures/get_works/work_entities.json"), new TypeReference<List<Work>>() {
    })
    1 * workRepository.getWorksForNamespace(namespace) >> workEntities

    expect:
    query.apply(namespace) == expected

  }
}
