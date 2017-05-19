package com.aakn.workstore.client.internal

import spock.lang.Specification

import javax.ws.rs.core.UriBuilder

class HystrixExternalWorkClientSpec extends Specification {

  private HystrixExternalWorkClient client
  private GetExternalWorkCommand getExternalWorkCommand = Mock()

  void setup() {
    client = new HystrixExternalWorkClient({ getExternalWorkCommand })
  }

  def "should call the command when getting works"() {
    setup:
    URI uri = UriBuilder.fromUri("http://localhost").build()

    when:
    client.getWorks(uri)

    then:
    1 * getExternalWorkCommand.uri(uri) >> getExternalWorkCommand
    1 * getExternalWorkCommand.execute()

  }
}
