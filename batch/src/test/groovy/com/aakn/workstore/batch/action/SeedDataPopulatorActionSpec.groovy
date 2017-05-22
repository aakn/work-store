package com.aakn.workstore.batch.action

import com.aakn.workstore.batch.config.SeedDataConfiguration
import com.aakn.workstore.batch.dto.BatchWorkRequest
import com.aakn.workstore.work.model.Work
import com.aakn.workstore.work.repository.WorkRepository
import spock.lang.Specification

class SeedDataPopulatorActionSpec extends Specification {

  SeedDataPopulatorAction action
  WorkRepository workRepository = Mock()
  ProcessBatchRequestAction processBatchRequestAction = Mock()

  void setup() {
    action = new SeedDataPopulatorAction(new SeedDataConfiguration(namespace: "test", url: "http://localhost"),
      workRepository, processBatchRequestAction)
  }

  def "should populate seed data if works is empty"() {
    when:
    workRepository.getWorksForNamespace("test") >> []
    action.accept()

    then:
    1 * processBatchRequestAction.accept(new BatchWorkRequest(directory: "test", url: "http://localhost"))
  }

  def "should do nothing if works is not empty"() {
    when:
    workRepository.getWorksForNamespace("test") >> [new Work()]
    action.accept()

    then:
    0 * processBatchRequestAction.accept(_)
  }
}
