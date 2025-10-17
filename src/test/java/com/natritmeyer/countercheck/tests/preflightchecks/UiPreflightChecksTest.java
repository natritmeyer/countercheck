package com.natritmeyer.countercheck.tests.preflightchecks;

import com.natritmeyer.countercheck.config.CountercheckApiTestConfig;
import java.time.Duration;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;

@SpringJUnitConfig(CountercheckApiTestConfig.class)
@TestPropertySource("classpath:/preflightchecks.properties")
public class UiPreflightChecksTest {
  private final WebTestClient webTestClient;
  private final int pollForSeconds;
  private final int pollingIntervalSeconds;
  private final String uisServiceScheme;
  private final String uiServiceHost;
  private final int uiServicePort;
  private final String uiServiceLivenessCheckPath;

  @Autowired
  public UiPreflightChecksTest(WebTestClient webTestClient,
                               @Value("${countercheck.preflightchecks.poll-for-seconds}") int pollForSeconds,
                               @Value("${countercheck.preflightchecks.polling-interval-seconds}") int pollingIntervalSeconds,
                               @Value("${countercheck.aut.ui-service.scheme}") String uisServiceScheme,
                               @Value("${countercheck.aut.ui-service.host}") String uiServiceHost,
                               @Value("${countercheck.aut.ui-service.port}") int uiServicePort,
                               @Value("${countercheck.aut.ui-service.liveness-check-path}") String uiServiceLivenessCheckPath) {
    this.webTestClient = webTestClient;
    this.pollForSeconds = pollForSeconds;
    this.pollingIntervalSeconds = pollingIntervalSeconds;
    this.uisServiceScheme = uisServiceScheme;
    this.uiServiceHost = uiServiceHost;
    this.uiServicePort = uiServicePort;
    this.uiServiceLivenessCheckPath = uiServiceLivenessCheckPath;
  }

  @Test
  public void waitForUi() {
    Awaitility.await("Wait for UI Service")
        .atMost(Duration.ofSeconds(pollForSeconds))
        .pollInterval(Duration.ofSeconds(pollingIntervalSeconds))
        .ignoreException(WebClientRequestException.class)
        .logging()
        .until(() ->
            webTestClient
                .get()
                .uri(builder -> builder
                    .scheme(uisServiceScheme)
                    .host(uiServiceHost)
                    .port(uiServicePort)
                    .path(uiServiceLivenessCheckPath)
                    .build())
                .exchange()
                .returnResult(Object.class)
                .getStatus()
                .value() == 200
        );
  }
}
