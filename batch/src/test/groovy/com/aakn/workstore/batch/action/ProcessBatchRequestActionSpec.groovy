package com.aakn.workstore.batch.action

import com.aakn.workstore.batch.dto.BatchWorkRequest
import com.aakn.workstore.client.ExternalWorkClient
import com.aakn.workstore.client.dto.Works
import com.aakn.workstore.work.model.Work
import com.aakn.workstore.work.repository.WorkRepository
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import io.dropwizard.jackson.Jackson
import spock.lang.Specification

import javax.ws.rs.core.UriBuilder

import static io.dropwizard.testing.FixtureHelpers.fixture

class ProcessBatchRequestActionSpec extends Specification {

  private ProcessBatchRequestAction processBatchRequestAction
  private ExternalWorkClient client = Mock()
  private WorkRepository workRepository = Mock()
  private ObjectMapper mapper

  void setup() {
    processBatchRequestAction = new ProcessBatchRequestAction(client, workRepository)
    mapper = Jackson.newObjectMapper()
  }

  def "should call the client to get works and then persist each work"() {
    setup:
    URI uri = UriBuilder.fromUri("http://localhost").build()
    BatchWorkRequest request = new BatchWorkRequest(url: uri, directory: "test")
    Works externalWorks = mapper.readValue(fixture("fixtures/process_batch/works.json"), Works.class)
    List<Work> expectedEntities = mapper.readValue(fixture("fixtures/process_batch/expected_works.json"), new TypeReference<List<Work>>() {
    })

    when:
    processBatchRequestAction.accept(request)

    then:
    1 * client.getWorks(uri) >> externalWorks
    1 * workRepository.persist(expectedEntities.get(0))
    1 * workRepository.persist(expectedEntities.get(1))
    1 * workRepository.persist(expectedEntities.get(2))
    1 * workRepository.persist(expectedEntities.get(3))
    1 * workRepository.persist(expectedEntities.get(4))

  }
}
