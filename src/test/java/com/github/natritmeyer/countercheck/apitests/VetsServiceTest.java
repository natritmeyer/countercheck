package com.github.natritmeyer.countercheck.apitests;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.natritmeyer.countercheck.config.WebTestClientConfig;
import com.github.natritmeyer.countercheck.domain.Vet;
import com.github.natritmeyer.countercheck.testdata.TestDataRetriever;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringJUnitConfig({WebTestClientConfig.class, TestDataRetriever.class})
public class VetsServiceTest {
  private static final String VETS_PATH = "vets";
  private static final int EXPECTED_NUMBER_OF_VETS = 6;

  private final WebTestClient webTestClient;
  private final String vetsServiceScheme;
  private final String vetsServiceHost;
  private final int vetsServicePort;
  private final List<Vet> expectedVets;

  @Autowired
  public VetsServiceTest(TestDataRetriever testDataRetriever,
                         WebTestClient webTestClient,
                         @Value("${countercheck.aut.vets-service.scheme}") String vetsServiceScheme,
                         @Value("${countercheck.aut.vets-service.host}") String vetsServiceHost,
                         @Value("${countercheck.aut.vets-service.port}") int vetsServicePort,
                         @Value("classpath:/testdata/api/expectedVets.json") Resource expectedVetsFile) {
    this.webTestClient = webTestClient;
    this.vetsServiceScheme = vetsServiceScheme;
    this.vetsServiceHost = vetsServiceHost;
    this.vetsServicePort = vetsServicePort;

    this.expectedVets = testDataRetriever.fromJsonFile(expectedVetsFile, new TypeReference<List<Vet>>() {
    });
  }

  @Test
  public void canRetrieveAListOfVets() {
    webTestClient
        .get()
        .uri(builder -> builder
            .scheme(vetsServiceScheme)
            .host(vetsServiceHost)
            .port(vetsServicePort)
            .path(VETS_PATH)
            .build())
        .exchange()
        .expectStatus()
        .isOk()
        .expectBodyList(Vet.class)
        .hasSize(EXPECTED_NUMBER_OF_VETS);
  }

  @Test
  public void canRetrieveVetDetails() {
    List<Vet> actualVets = webTestClient
        .get()
        .uri(builder -> builder
            .scheme(vetsServiceScheme)
            .host(vetsServiceHost)
            .port(vetsServicePort)
            .path(VETS_PATH)
            .build())
        .exchange()
        .expectBodyList(Vet.class)
        .returnResult()
        .getResponseBody();

    assertThat(actualVets).containsExactlyInAnyOrderElementsOf(expectedVets);
  }
}
