package com.aakn.workstore.batch.resource

import com.aakn.workstore.batch.action.ProcessBatchRequestAction
import com.aakn.workstore.batch.dto.BatchWorkRequest
import com.squarespace.jersey2.guice.JerseyGuiceUtils
import io.dropwizard.testing.junit.ResourceTestRule
import org.junit.Rule
import spock.lang.Specification

import javax.ws.rs.core.UriBuilder

import static javax.ws.rs.client.Entity.json

class BatchResourceSpec extends Specification {

  ProcessBatchRequestAction processBatchRequest = Mock()

  @Rule
  ResourceTestRule resources = ResourceTestRule.builder()
    .addResource(new BatchResource(processBatchRequest))
    .build()

  // fixing the service locator issue as per https://github.com/HubSpot/dropwizard-guice/issues/95#issuecomment-274851181
  def setupSpec() {
    JerseyGuiceUtils.reset()
  }

  def "should call the process request"() {
    setup:
    def request = new BatchWorkRequest(url: UriBuilder.fromUri("http://localhost/test").build(), directory: "test")

    when:
    def response = resources.target("/api/works").request().post(json(request))

    then:
    response.status == 202

    then:
    1 * processBatchRequest.accept(request)
  }
}