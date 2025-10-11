package com.github.natritmeyer.countercheck.tests.apitests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.natritmeyer.countercheck.config.CountercheckConfig;
import com.github.natritmeyer.countercheck.model.domain.Owner;
import com.github.natritmeyer.countercheck.model.domain.Pet;
import com.github.natritmeyer.countercheck.model.domain.PetType;
import com.github.natritmeyer.countercheck.model.requestbodies.OwnerRequest;
import com.github.natritmeyer.countercheck.model.requestbodies.PetRequest;
import com.github.natritmeyer.countercheck.support.testdata.TestDataRetriever;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.util.DefaultUriBuilderFactory;

@SpringJUnitConfig(CountercheckConfig.class)
public class CustomersServiceTest {
  private static final String OWNERS_PATH = "owners";

  private final WebTestClient webTestClient;
  private final String customersServiceScheme;
  private final String customersServiceHost;
  private final int customersServicePort;

  private final List<PetType> expectedPetTypes;
  private final List<Owner> expectedOwners;

  @Autowired
  public CustomersServiceTest(TestDataRetriever testDataRetriever,
                              WebTestClient webTestClient,
                              @Value("${countercheck.aut.customers-service.scheme}") String customersServiceScheme,
                              @Value("${countercheck.aut.customers-service.host}") String customersServiceHost,
                              @Value("${countercheck.aut.customers-service.port}") int customersServicePort,
                              @Value("classpath:/testdata/api/expectedPetTypes.json") Resource expectedPetTypesFile,
                              @Value("classpath:/testdata/api/expectedOwners.json") Resource expectedOwnersFile
  ) {
    this.webTestClient = webTestClient;
    this.customersServiceScheme = customersServiceScheme;
    this.customersServiceHost = customersServiceHost;
    this.customersServicePort = customersServicePort;

    this.expectedPetTypes = testDataRetriever.fromJsonFile(expectedPetTypesFile, new TypeReference<>() {
    });
    this.expectedOwners = testDataRetriever.fromJsonFile(expectedOwnersFile, new TypeReference<>() {
    });
  }

  @Nested
  class PetTypesTests {
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

  @Nested
  class OwnersTests {
    private static final String FIRST_NAME = "Sam";
    private static final String LAST_NAME = "Gamgee";
    private static final String ADDRESS = "3 Bagshot Row, Hobbiton";
    private static final String CITY = "The Shire";
    private static final String TELEPHONE = "123456789012";

    @Test
    public void canRetrieveStartingListOfOwners() {
      List<Owner> actualOwners = webTestClient
          .get()
          .uri(builder ->
              builder.scheme(customersServiceScheme)
                  .host(customersServiceHost)
                  .port(customersServicePort)
                  .path(OWNERS_PATH)
                  .build())
          .exchange()
          .expectStatus()
          .isOk()
          .expectBodyList(Owner.class)
          .returnResult()
          .getResponseBody();

      assertThat(actualOwners).containsAll(expectedOwners);
    }

    @Test
    public void canRetrieveIndividualOwnersDetails() {
      Owner owner = webTestClient
          .get()
          .uri(builder ->
              builder.scheme(customersServiceScheme)
                  .host(customersServiceHost)
                  .port(customersServicePort)
                  .path(OWNERS_PATH + "/1")
                  .build())
          .exchange()
          .expectStatus()
          .isOk()
          .expectBody(Owner.class)
          .returnResult()
          .getResponseBody();

      assertSoftly(softly -> {
        softly.assertThat(owner.getId()).isEqualTo(1);
        softly.assertThat(owner.getFirstName()).isEqualTo("George");
        softly.assertThat(owner.getLastName()).isEqualTo("Franklin");
        softly.assertThat(owner.getAddress()).isEqualTo("110 W. Liberty St.");
        softly.assertThat(owner.getCity()).isEqualTo("Madison");
        softly.assertThat(owner.getTelephone()).isEqualTo("6085551023");
        softly.assertThat(owner.getPets()).hasSize(1);

        Pet customersPet = owner.getPets().get(0);
        softly.assertThat(customersPet.getId()).isEqualTo(1);
        softly.assertThat(customersPet.getName()).isEqualTo("Leo");
        softly.assertThat(customersPet.getBirthDate()).isEqualTo(LocalDate.of(2010, 9, 7));

        PetType customersPetType = customersPet.getPetType();
        softly.assertThat(customersPetType.getId()).isEqualTo(1);
        softly.assertThat(customersPetType.getName()).isEqualTo("cat");
      });
    }

    @Test
    public void canCreateNewOwner() {
      OwnerRequest newOwnerRequest = new OwnerRequest(FIRST_NAME, LAST_NAME, ADDRESS, CITY, TELEPHONE);

      Owner createdOwner = webTestClient
          .post()
          .uri(builder ->
              builder.scheme(customersServiceScheme)
                  .host(customersServiceHost)
                  .port(customersServicePort)
                  .path(OWNERS_PATH)
                  .build())
          .body(BodyInserters.fromValue(newOwnerRequest))
          .exchange()
          .expectStatus()
          .isCreated()
          .expectBody(Owner.class)
          .returnResult()
          .getResponseBody();

      assertSoftly(softly -> {
        softly.assertThat(createdOwner.getId()).isPositive();
        softly.assertThat(createdOwner.getFirstName()).isEqualTo(FIRST_NAME);
        softly.assertThat(createdOwner.getLastName()).isEqualTo(LAST_NAME);
        softly.assertThat(createdOwner.getAddress()).isEqualTo(ADDRESS);
        softly.assertThat(createdOwner.getCity()).isEqualTo(CITY);
        softly.assertThat(createdOwner.getTelephone()).isEqualTo(TELEPHONE);
        softly.assertThat(createdOwner.getPets()).isEmpty();
      });
    }

    @Test
    public void canUpdateOwnerDetails() {
      OwnerRequest newOwnerRequest = new OwnerRequest(FIRST_NAME, LAST_NAME, ADDRESS, CITY, TELEPHONE);

      // create the owner
      Owner createdOwner = webTestClient
          .post()
          .uri(builder ->
              builder.scheme(customersServiceScheme)
                  .host(customersServiceHost)
                  .port(customersServicePort)
                  .path(OWNERS_PATH)
                  .build())
          .body(BodyInserters.fromValue(newOwnerRequest))
          .exchange()
          .expectStatus()
          .isCreated()
          .expectBody(Owner.class)
          .returnResult()
          .getResponseBody();

      URI createdOwnerUri = new DefaultUriBuilderFactory()
          .builder()
          .scheme(customersServiceScheme)
          .host(customersServiceHost)
          .port(customersServicePort)
          .path(String.format("%s/%s", OWNERS_PATH, createdOwner.getId()))
          .build();

      // update the owner's details
      String newAddress = "Bag End, Hobbiton";
      OwnerRequest updatedOwnerRequest = new OwnerRequest(FIRST_NAME, LAST_NAME, newAddress, CITY, TELEPHONE);

      webTestClient
          .put()
          .uri(createdOwnerUri)
          .body(BodyInserters.fromValue(updatedOwnerRequest))
          .exchange()
          .expectStatus()
          .isNoContent();

      // retrieve the owner
      Owner updatedOwner = webTestClient
          .get()
          .uri(createdOwnerUri)
          .exchange()
          .expectStatus()
          .isOk()
          .expectBody(Owner.class)
          .returnResult()
          .getResponseBody();

      assertSoftly(softly -> {
        softly.assertThat(updatedOwner.getId()).isEqualTo(createdOwner.getId());
        softly.assertThat(updatedOwner.getAddress()).isEqualTo(newAddress);
      });
    }
  }

  @Nested
  class OwnershipTests {
    private static final String FIRST_NAME = "Gandalf";
    private static final String LAST_NAME = "The Grey";
    private static final String ADDRESS = "Shadowfax's Saddle";
    private static final String CITY = "Middle Earth";
    private static final String TELEPHONE = "987654321098";
    private static final String PET_NAME = "Gwaihir";

    @Test
    public void canGiveAnOwnerAPet() {
      OwnerRequest newOwnerRequest = new OwnerRequest(FIRST_NAME, LAST_NAME, ADDRESS, CITY, TELEPHONE);

      Owner createdOwner = webTestClient
          .post()
          .uri(builder ->
              builder.scheme(customersServiceScheme)
                  .host(customersServiceHost)
                  .port(customersServicePort)
                  .path(OWNERS_PATH)
                  .build())
          .body(BodyInserters.fromValue(newOwnerRequest))
          .exchange()
          .expectStatus()
          .isCreated()
          .expectBody(Owner.class)
          .returnResult()
          .getResponseBody();

      PetRequest gwaihirRequest = new PetRequest(PET_NAME, LocalDate.of(2000, 10, 20), 5);

      webTestClient
          .post()
          .uri(builder -> builder
              .scheme(customersServiceScheme)
              .host(customersServiceHost)
              .port(customersServicePort)
              .path(String.format("%s/%s/pets", OWNERS_PATH, createdOwner.getId()))
              .build()
          )
          .body(BodyInserters.fromValue(gwaihirRequest))
          .exchange()
          .expectStatus()
          .isCreated();

      Owner gandalfOwningGwaihir = webTestClient
          .get()
          .uri(builder -> builder
              .scheme(customersServiceScheme)
              .host(customersServiceHost)
              .port(customersServicePort)
              .path(String.format("%s/%s", OWNERS_PATH, createdOwner.getId()))
              .build())
          .exchange()
          .expectStatus()
          .isOk()
          .expectBody(Owner.class)
          .returnResult()
          .getResponseBody();

      assertSoftly(softly -> {
        softly.assertThat(gandalfOwningGwaihir.getPets()).hasSize(1);
        softly.assertThat(gandalfOwningGwaihir.getPets().get(0).getName()).isEqualTo(PET_NAME);
      });
    }
  }
}
