package com.aakn.workstore.client.internal

import com.aakn.workstore.client.dto.Works
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.netflix.hystrix.exception.HystrixBadRequestException
import com.netflix.hystrix.exception.HystrixRuntimeException
import groovy.util.logging.Slf4j
import io.dropwizard.client.JerseyClientBuilder
import io.dropwizard.client.JerseyClientConfiguration
import io.dropwizard.jackson.Jackson
import io.dropwizard.testing.junit.DropwizardClientRule
import org.junit.ClassRule
import spock.lang.Specification

import javax.ws.rs.BadRequestException
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.client.Client
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.core.UriBuilder

import static io.dropwizard.testing.FixtureHelpers.fixture

@Slf4j
class GetExternalWorkCommandSpec extends Specification {

  @ClassRule
  public static final DropwizardClientRule dropwizard =
    new DropwizardClientRule(new ExternalWorkResource())
  private GetExternalWorkCommand command
  private ObjectMapper mapper

  void setup() {
    dropwizard.testSupport.before()
    Client client = new JerseyClientBuilder(dropwizard.environment)
      .using(new JerseyClientConfiguration())
      .build("test-client")
    command = new GetExternalWorkCommand(client)
    mapper = Jackson.newObjectMapper()
  }

  void cleanup() {
    dropwizard.testSupport.after()
  }

  def "should throw up when not an XML"() {
    setup:
    URI uri = UriBuilder.fromUri(dropwizard.baseUri()).path("/api/v1/bad_works.xml").build()

    when:
    command.uri(uri).execute()

    then:
    def exception = thrown(HystrixRuntimeException.class)
    exception.cause.class == BadRequestException.class
  }

  def "should throw up when not 200"() {
    setup:
    URI uri = UriBuilder.fromUri(dropwizard.baseUri()).path("/api/v1/400.xml").build()

    when:
    command.uri(uri).execute()

    then:
    def exception = thrown(HystrixBadRequestException.class)
    exception.message == "Request failed with status: 400. Response: oops"
  }

  def "should return works"() {
    setup:
    URI uri = UriBuilder.fromUri(dropwizard.baseUri()).path("/api/v1/works.xml").build()

    expect:
    def works = command.uri(uri).execute()
    works == mapper.readValue(fixture("fixtures/clients/expected/works.json"), Works.class)

  }


  @Path("/api/v1")
  static class ExternalWorkResource {

    private final ObjectMapper mapper

    ExternalWorkResource() {
      mapper = Jackson.newObjectMapper()
      new XmlMapper()
    }

    @GET
    @Path("/bad_works.xml")
    String badWorks() {
      return "bad works!"
    }

    @GET
    @Path("/400.xml")
    Response fourHundred() {
      return Response.status(400)
        .entity("oops")
        .build()
    }

    @GET
    @Path("/works.xml")
    @Produces(MediaType.APPLICATION_XML)
    Response works() {
      return Response.ok(fixture("fixtures/clients/works.xml")).build()
    }

  }
}
