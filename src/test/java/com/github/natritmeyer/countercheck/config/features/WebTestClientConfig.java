package com.github.natritmeyer.countercheck.config.features;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.netty.http.client.HttpClient;

@Configuration
@PropertySource("classpath:/countercheck.properties")
public class WebTestClientConfig {
  @Bean
  public WebTestClient getWebTestClient(@Value("${countercheck.request.timeout}") int timeoutSeconds) {
    HttpClient httpClient = HttpClient.create()
        .responseTimeout(Duration.ofSeconds(timeoutSeconds));

    return WebTestClient
        .bindToServer()
        .responseTimeout(Duration.ofSeconds(timeoutSeconds))
        .clientConnector(new ReactorClientHttpConnector(httpClient))
        .build();
  }
}
