package com.aakn.workstore.work.query

import com.aakn.workstore.work.dto.NamesResponse
import com.aakn.workstore.work.repository.WorkRepository
import spock.lang.Specification

class GetMakeNamesQuerySpec extends Specification {

  private GetMakeNamesQuery query
  private WorkRepository workRepository = Mock()

  void setup() {
    query = new GetMakeNamesQuery(workRepository)
  }

  def "should return a response with a list of names"() {
    given:
    String namespace = "test"
    1 * workRepository.getUniqueMakeNames(namespace) >> ["LEICA", "NIKON", "CANON"]

    expect:
    query.apply(namespace) == new NamesResponse(names: ["LEICA", "NIKON", "CANON"])

  }
}
