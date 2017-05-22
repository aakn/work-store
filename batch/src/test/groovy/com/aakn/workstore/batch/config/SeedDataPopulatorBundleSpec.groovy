package com.aakn.workstore.batch.config

import com.aakn.test.hibernate.DAOTestRule
import com.aakn.workstore.batch.action.SeedDataPopulatorAction
import com.aakn.workstore.work.model.Work
import org.junit.Rule
import spock.lang.Specification

class SeedDataPopulatorBundleSpec extends Specification {

  private SeedDataPopulatorBundle bundle

  @Rule
  public DAOTestRule database = DAOTestRule.newBuilder()
    .addEntityClass(Work.class)
    .setShowSql(true)
    .addResource("queries.hbm.xml")
    .build()
  private SeedDataPopulatorAction action = Mock()
  private SeedDataConfiguration configuration

  void setup() {
    configuration = new SeedDataConfiguration()
    bundle = new SeedDataPopulatorBundle({ configuration }, { database.sessionFactory }, { action })
  }

  def "should not do anything if config is disabled"() {
    given:
    configuration.enabled = false
    when:
    bundle.start()

    then:
    0 * action.accept()
  }

  def "should call action if config is enabled"() {
    when:
    configuration.enabled = true
    bundle.start()

    then:
    1 * action.accept()
  }
}
