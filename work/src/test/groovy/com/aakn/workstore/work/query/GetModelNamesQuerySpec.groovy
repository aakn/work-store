package com.aakn.workstore.work.query

import com.aakn.workstore.work.dto.NamesResponse
import com.aakn.workstore.work.repository.WorkRepository
import spock.lang.Specification

class GetModelNamesQuerySpec extends Specification {

  private GetModelNamesQuery query
  private WorkRepository workRepository = Mock()

  void setup() {
    query = new GetModelNamesQuery(workRepository)
  }

  def "should return a response with a list of names"() {
    given:
    String namespace = "test"
    String make = "make"
    1 * workRepository.getUniqueModelNames(namespace, make) >> ["D-LUX 3", "D-LUX 4"]

    expect:
    query.apply(namespace, make) == new NamesResponse(names: ["D-LUX 3", "D-LUX 4"])

  }
}
