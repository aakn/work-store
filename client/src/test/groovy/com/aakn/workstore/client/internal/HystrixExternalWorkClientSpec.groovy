package com.aakn.workstore.client.internal

import com.aakn.workstore.client.exception.ExternalWorkApiException
import com.netflix.hystrix.exception.HystrixRuntimeException
import spock.lang.Specification

import javax.ws.rs.core.UriBuilder

import static com.netflix.hystrix.exception.HystrixRuntimeException.FailureType.COMMAND_EXCEPTION

class HystrixExternalWorkClientSpec extends Specification {

  private HystrixExternalWorkClient client
  private GetExternalWorkCommand getExternalWorkCommand = Mock()

  void setup() {
    client = new HystrixExternalWorkClient({ getExternalWorkCommand })
  }

  def "should call the command when getting works"() {
    given:
    URI uri = UriBuilder.fromUri("http://localhost").build()
    1 * getExternalWorkCommand.uri(uri) >> getExternalWorkCommand

    when:
    client.getWorks(uri)

    then:
    1 * getExternalWorkCommand.execute()
  }

  def "should throw ExternalWorkException when command throws an exception"() {
    given:
    URI uri = UriBuilder.fromUri("http://localhost").build()
    1 * getExternalWorkCommand.uri(uri) >> getExternalWorkCommand
    1 * getExternalWorkCommand.execute() >> { throw new HystrixRuntimeException(COMMAND_EXCEPTION, null, "test", null, null) }

    when:
    client.getWorks(uri)

    then:
    ExternalWorkApiException e = thrown()
    e.params.uri == "http://localhost"
    e.message == "test"
  }
}
