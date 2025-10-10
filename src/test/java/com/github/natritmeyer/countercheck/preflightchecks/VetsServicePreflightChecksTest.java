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
public class VetsServicePreflightChecksTest {
  private final WebTestClient webTestClient;
  private final int pollForSeconds;
  private final int pollingIntervalSeconds;
  private final String vetsServiceScheme;
  private final String vetsServiceHost;
  private final int vetsServicePort;
  private final String vetsServiceLivenessCheckPath;

  @Autowired
  public VetsServicePreflightChecksTest(WebTestClient webTestClient,
                                        @Value("${countercheck.preflightchecks.poll-for-seconds}") int pollForSeconds,
                                        @Value("${countercheck.preflightchecks.polling-interval-seconds}") int pollingIntervalSeconds,
                                        @Value("${countercheck.aut.vets-service.scheme}") String vetsServiceScheme,
                                        @Value("${countercheck.aut.vets-service.host}") String vetsServiceHost,
                                        @Value("${countercheck.aut.vets-service.port}") int vetsServicePort,
                                        @Value("${countercheck.aut.vets-service.liveness-check-path}") String vetsServiceLivenessCheckPath
  ) {
    this.webTestClient = webTestClient;
    this.pollForSeconds = pollForSeconds;
    this.pollingIntervalSeconds = pollingIntervalSeconds;
    this.vetsServiceScheme = vetsServiceScheme;
    this.vetsServiceHost = vetsServiceHost;
    this.vetsServicePort = vetsServicePort;
    this.vetsServiceLivenessCheckPath = vetsServiceLivenessCheckPath;
  }

  @Test
  public void waitForVetsService() {
    Awaitility.await("Wait for Vets Service")
        .atMost(Duration.ofSeconds(pollForSeconds))
        .pollInterval(Duration.ofSeconds(pollingIntervalSeconds))
        .ignoreException(WebClientRequestException.class)
        .logging()
        .until(() ->
            webTestClient
                .get()
                .uri(builder -> builder
                    .scheme(vetsServiceScheme)
                    .host(vetsServiceHost)
                    .port(vetsServicePort)
                    .path(vetsServiceLivenessCheckPath)
                    .build())
                .exchange()
                .returnResult(Object.class)
                .getStatus()
                .value() == 200
        );
  }
}
