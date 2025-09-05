package com.github.natritmeyer.countercheck.preflightchecks;

import com.github.natritmeyer.countercheck.config.WebTestClientConfig;
import java.time.Duration;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringJUnitConfig(WebTestClientConfig.class)
public class PreflightChecksTest {
  private final WebTestClient webTestClient;
  private final String customerServiceScheme;
  private final String customerServiceHost;
  private final int customerServicePort;
  private final String customerServiceLivenessCheckPath;

  @Autowired
  public PreflightChecksTest(WebTestClient webTestClient,
                             @Value("${countercheck.aut.customer-service.scheme}") String customerServiceScheme,
                             @Value("${countercheck.aut.customer-service.host}") String customerServiceHost,
                             @Value("${countercheck.aut.customer-service.port}") int customerServicePort,
                             @Value("${countercheck.aut.customer-service.liveness-check-path}") String customerServiceLivenessCheckPath) {
    this.webTestClient = webTestClient;
    this.customerServiceScheme = customerServiceScheme;
    this.customerServiceHost = customerServiceHost;
    this.customerServicePort = customerServicePort;
    this.customerServiceLivenessCheckPath = customerServiceLivenessCheckPath;
  }

  @Test
  public void waitForCustomerService() {
    Awaitility.await("Wait for Customer Service")
        .pollInterval(Duration.ofSeconds(3))
        .until(() ->
            webTestClient
                .get()
                .uri(builder -> builder
                    .scheme(customerServiceScheme)
                    .host(customerServiceHost)
                    .port(customerServicePort)
                    .path(customerServiceLivenessCheckPath)
                    .build())
                .exchange()
                .returnResult(Object.class)
                .getStatus()
                .value() == 200
        );
  }
}
