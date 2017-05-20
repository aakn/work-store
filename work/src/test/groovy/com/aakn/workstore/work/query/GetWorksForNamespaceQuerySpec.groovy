package com.aakn.workstore.work.query

import com.aakn.workstore.work.dto.WorksResponse
import com.aakn.workstore.work.function.WorkEntityToResponseFunction
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
  private WorkEntityToResponseFunction workEntityToResponseFunction = Mock()

  void setup() {
    query = new GetWorksForNamespaceQuery(workRepository, workEntityToResponseFunction)
    mapper = Jackson.newObjectMapper()
  }

  def "should return works"() {
    given:
    String namespace = "test"
    WorksResponse expected = mapper.readValue(fixture("fixtures/get_works/expected_namespace_response.json"), WorksResponse.class)
    List<Work> workEntities = mapper.readValue(fixture("fixtures/get_works/work_entities_for_namespace.json"), new TypeReference<List<Work>>() {
    })
    1 * workRepository.getWorksForNamespace(namespace) >> workEntities
    1 * workEntityToResponseFunction.apply(workEntities) >> expected

    expect:
    query.apply(namespace) == expected

  }
}