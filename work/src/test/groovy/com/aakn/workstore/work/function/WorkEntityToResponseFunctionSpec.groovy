package com.aakn.workstore.work.function

import com.aakn.workstore.work.dto.WorksResponse
import com.aakn.workstore.work.model.Work
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import io.dropwizard.jackson.Jackson
import spock.lang.Specification
import spock.lang.Unroll

import static io.dropwizard.testing.FixtureHelpers.fixture

class WorkEntityToResponseFunctionSpec extends Specification {

  private WorkEntityToResponseFunction function
  private ObjectMapper mapper

  void setup() {
    function = new WorkEntityToResponseFunction()
    mapper = Jackson.newObjectMapper()
  }

  @Unroll
  def "should convert entities to works response for #type"() {
    given:
    WorksResponse expected = mapper.readValue(fixture("fixtures/get_works/expected_${type}_response.json"), WorksResponse.class)
    List<Work> workEntities = mapper.readValue(fixture("fixtures/get_works/work_entities_for_${type}.json"), new TypeReference<List<Work>>() {
    })

    expect:
    function.apply(workEntities) == expected

    where:
    type        | _
    "namespace" | _
    "make"      | _
    "null"      | _

  }
}
