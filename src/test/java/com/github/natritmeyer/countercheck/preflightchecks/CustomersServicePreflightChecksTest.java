package com.github.natritmeyer.countercheck.preflightchecks;

import com.github.natritmeyer.countercheck.config.CountercheckConfig;
import java.time.Duration;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;

@SpringJUnitConfig(CountercheckConfig.class)
@TestPropertySource("classpath:/preflightchecks.properties")
public class CustomersServicePreflightChecksTest {
  private final WebTestClient webTestClient;
  private final int pollForSeconds;
  private final int pollingIntervalSeconds;
  private final String customersServiceScheme;
  private final String customersServiceHost;
  private final int customersServicePort;
  private final String customersServiceLivenessCheckPath;

  @Autowired
  public CustomersServicePreflightChecksTest(WebTestClient webTestClient,
                                             @Value("${countercheck.preflightchecks.poll-for-seconds}") int pollForSeconds,
                                             @Value("${countercheck.preflightchecks.polling-interval-seconds}") int pollingIntervalSeconds,
                                             @Value("${countercheck.aut.customers-service.scheme}") String customersServiceScheme,
                                             @Value("${countercheck.aut.customers-service.host}") String customersServiceHost,
                                             @Value("${countercheck.aut.customers-service.port}") int customersServicePort,
                                             @Value("${countercheck.aut.customers-service.liveness-check-path}") String customersServiceLivenessCheckPath
  ) {
    this.webTestClient = webTestClient;
    this.pollForSeconds = pollForSeconds;
    this.pollingIntervalSeconds = pollingIntervalSeconds;
    this.customersServiceScheme = customersServiceScheme;
    this.customersServiceHost = customersServiceHost;
    this.customersServicePort = customersServicePort;
    this.customersServiceLivenessCheckPath = customersServiceLivenessCheckPath;
  }

  @Test
  public void waitForCustomersService() {
    Awaitility.await("Wait for Customers Service")
        .atMost(Duration.ofSeconds(pollForSeconds))
        .pollInterval(Duration.ofSeconds(pollingIntervalSeconds))
        .ignoreException(WebClientRequestException.class)
        .logging()
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
}
