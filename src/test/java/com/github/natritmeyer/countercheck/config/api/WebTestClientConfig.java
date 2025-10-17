package com.github.natritmeyer.countercheck.config.api;

import com.github.natritmeyer.countercheck.support.common.ProxyManager;
import java.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.ProxyProvider;

@Configuration
public class WebTestClientConfig {
  private final Logger log = LoggerFactory.getLogger(this.getClass());

  private final int timeoutSeconds;
  private final ProxyManager proxyManager;

  @Autowired
  public WebTestClientConfig(@Value("${countercheck.request.timeout}") int timeoutSeconds,
                             ProxyManager proxyManager) {
    this.timeoutSeconds = timeoutSeconds;
    this.proxyManager = proxyManager;
  }

  @Bean
  public WebTestClient getWebTestClient() {
    HttpClient httpClient = HttpClient.create()
        .responseTimeout(Duration.ofSeconds(timeoutSeconds))
        .proxyWhen((httpClientConfig, spec) -> {
          log.debug("Proxy config: {}", this.proxyManager.toString());
          if (this.proxyManager.isProxyRequired()) {
            return Mono.justOrEmpty(spec
                .type(ProxyProvider.Proxy.HTTP)
                .host(this.proxyManager.getProxyHost())
                .port(this.proxyManager.getProxyPort())
                .username(this.proxyManager.getProxyUsername())
                .password((username) -> this.proxyManager.getProxyPassword()));
          } else {
            return Mono.empty();
          }
        });

    return WebTestClient
        .bindToServer()
        .responseTimeout(Duration.ofSeconds(timeoutSeconds))
        .clientConnector(new ReactorClientHttpConnector(httpClient))
        .build();
  }
}
