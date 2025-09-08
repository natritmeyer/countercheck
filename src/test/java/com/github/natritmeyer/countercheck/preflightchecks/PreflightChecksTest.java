package com.github.natritmeyer.countercheck.preflightchecks;

import com.github.natritmeyer.countercheck.config.WebTestClientConfig;
import java.time.Duration;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;

@SpringJUnitConfig(WebTestClientConfig.class)
@TestPropertySource("classpath:/preflightchecks.properties")
public class PreflightChecksTest {
  private final WebTestClient webTestClient;
  private final int pollForSeconds;
  private final int pollingIntervalSeconds;
  private final String customersServiceScheme;
  private final String customersServiceHost;
  private final int customersServicePort;
  private final String customersServiceLivenessCheckPath;
  private final String visitsServiceScheme;
  private final String visitsServiceHost;
  private final int visitsServicePort;
  private final String visitsServiceLivenessCheckPath;

  @Autowired
  public PreflightChecksTest(WebTestClient webTestClient,
                             @Value("${countercheck.preflightchecks.poll-for-seconds}") int pollForSeconds,
                             @Value("${countercheck.preflightchecks.polling-interval-seconds}") int pollingIntervalSeconds,
                             @Value("${countercheck.aut.customers-service.scheme}") String customersServiceScheme,
                             @Value("${countercheck.aut.customers-service.host}") String customersServiceHost,
                             @Value("${countercheck.aut.customers-service.port}") int customersServicePort,
                             @Value("${countercheck.aut.customers-service.liveness-check-path}") String customersServiceLivenessCheckPath,
                             @Value("${countercheck.aut.visits-service.scheme}") String visitsServiceScheme,
                             @Value("${countercheck.aut.visits-service.host}") String visitsServiceHost,
                             @Value("${countercheck.aut.visits-service.port}") int visitsServicePort,
                             @Value("${countercheck.aut.visits-service.liveness-check-path}") String visitsServiceLivenessCheckPath) {
    this.webTestClient = webTestClient;
    this.pollForSeconds = pollForSeconds;
    this.pollingIntervalSeconds = pollingIntervalSeconds;
    this.customersServiceScheme = customersServiceScheme;
    this.customersServiceHost = customersServiceHost;
    this.customersServicePort = customersServicePort;
    this.customersServiceLivenessCheckPath = customersServiceLivenessCheckPath;
    this.visitsServiceScheme = visitsServiceScheme;
    this.visitsServiceHost = visitsServiceHost;
    this.visitsServicePort = visitsServicePort;
    this.visitsServiceLivenessCheckPath = visitsServiceLivenessCheckPath;
  }

  @Test
  public void waitForCustomersService() {
    Awaitility.await("Wait for Customers Service")
        .atMost(Duration.ofSeconds(pollForSeconds))
        .pollInterval(Duration.ofSeconds(pollingIntervalSeconds))
        .ignoreException(WebClientRequestException.class)
        .until(() ->
            webTestClient
                .get()
                .uri(builder -> builder
                    .scheme(customersServiceScheme)
                    .host(customersServiceHost)
                    .port(customersServicePort)
                    .path(customersServiceLivenessCheckPath)
                    .build())
                .exchange()
                .returnResult(Object.class)
                .getStatus()
                .value() == 200
        );
  }

  @Test
  public void waitForVisitsService() {
    Awaitility.await("Wait for Visits Service")
        .atMost(Duration.ofSeconds(pollForSeconds))
        .pollInterval(Duration.ofSeconds(pollingIntervalSeconds))
        .ignoreException(WebClientRequestException.class)
        .until(() ->
            webTestClient
                .get()
                .uri(builder -> builder
                    .scheme(visitsServiceScheme)
                    .host(visitsServiceHost)
                    .port(visitsServicePort)
                    .path(visitsServiceLivenessCheckPath)
                    .queryParam("petId", "1")
                    .build())
                .exchange()
                .returnResult(Object.class)
                .getStatus()
                .value() == 200
        );
  }
}
