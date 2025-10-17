package com.natritmeyer.countercheck.support.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProxyManager {
  private final boolean proxyRequired;
  private final String proxyHost;
  private final int proxyPort;
  private final String proxyUsername;
  private final String proxyPassword;

  @Autowired
  public ProxyManager(@Value("${countercheck.proxy.required}") boolean proxyRequired,
                      @Value("${countercheck.proxy.host}") String proxyHost,
                      @Value("${countercheck.proxy.port:33333}") int proxyPort,
                      @Value("${countercheck.proxy.username}") String proxyUsername,
                      @Value("${countercheck.proxy.password}") String proxyPassword) {
    this.proxyRequired = proxyRequired;
    this.proxyHost = proxyHost;
    this.proxyPort = proxyPort;
    this.proxyUsername = proxyUsername;
    this.proxyPassword = proxyPassword;
  }

  public boolean isProxyRequired() {
    return this.proxyRequired;
  }

  public String getProxyHost() {
    return proxyHost;
  }

  public int getProxyPort() {
    return proxyPort;
  }

  public String getProxyUsername() {
    return proxyUsername;
  }

  public String getProxyPassword() {
    return proxyPassword;
  }

  public String toString() {
    return proxyRequired ? String.format("Proxying through %s:%d with creds %s:%s",
        this.proxyHost,
        this.proxyPort,
        this.proxyUsername,
        this.proxyPassword)
        : "Proxy not required";
  }
}
