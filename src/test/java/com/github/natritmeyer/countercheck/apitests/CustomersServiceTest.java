package com.github.natritmeyer.countercheck.apitests;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.natritmeyer.countercheck.config.WebTestClientConfig;
import com.github.natritmeyer.countercheck.domain.PetType;
import com.github.natritmeyer.countercheck.testdata.TestDataRetriever;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringJUnitConfig({WebTestClientConfig.class, TestDataRetriever.class})
public class CustomersServiceTest {
  private final WebTestClient webTestClient;
  private final String customersServiceScheme;
  private final String customersServiceHost;
  private final int customersServicePort;

  private final List<PetType> expectedPetTypes;

  @Autowired
  public CustomersServiceTest(TestDataRetriever testDataRetriever,
                              WebTestClient webTestClient,
                              @Value("${countercheck.aut.customers-service.scheme}") String customersServiceScheme,
                              @Value("${countercheck.aut.customers-service.host}") String customersServiceHost,
                              @Value("${countercheck.aut.customers-service.port}") int customersServicePort,
                              @Value("classpath:/testdata/api/expectedPetTypes.json") Resource expectedPetTypesFile
  ) {
    this.webTestClient = webTestClient;
    this.customersServiceScheme = customersServiceScheme;
    this.customersServiceHost = customersServiceHost;
    this.customersServicePort = customersServicePort;

    this.expectedPetTypes = testDataRetriever.fromJsonFile(expectedPetTypesFile, new TypeReference<>() {
    });
  }

  @Nested
  public class PetTypesTests {
    public static final String PET_TYPES_PATH = "petTypes";

    @Test
    public void canRetrieveListOfPetTypes() {
      List<PetType> actualPetTypes = webTestClient
          .get()
          .uri(builder ->
              builder.scheme(customersServiceScheme)
                  .host(customersServiceHost)
                  .port(customersServicePort)
                  .path(PET_TYPES_PATH)
                  .build())
          .exchange()
          .expectStatus()
          .isOk()
          .expectBodyList(PetType.class)
          .returnResult()
          .getResponseBody();

      assertThat(actualPetTypes).containsExactlyInAnyOrderElementsOf(expectedPetTypes);
    }
  }
}
