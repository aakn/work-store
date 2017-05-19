package com.aakn.workstore.batch.action

import com.aakn.workstore.batch.dto.BatchWorkRequest
import com.aakn.workstore.client.ExternalWorkClient
import spock.lang.Specification

import javax.ws.rs.core.UriBuilder

class ProcessBatchRequestActionTest extends Specification {

  private ProcessBatchRequestAction processBatchRequestAction
  private ExternalWorkClient client = Mock()

  void setup() {
    processBatchRequestAction = new ProcessBatchRequestAction(client)
  }

  def "should call the client to get works"() {
    setup:
    URI uri = UriBuilder.fromUri("http://localhost").build()
    BatchWorkRequest request = new BatchWorkRequest(url: uri, directory: "test")

    when:
    processBatchRequestAction.accept(request)

    then:
    1 * client.getWorks(uri)

  }
}
