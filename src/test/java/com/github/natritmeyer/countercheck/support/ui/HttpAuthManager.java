package com.github.natritmeyer.countercheck.support.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HttpAuthManager {
  private final boolean httpAuthRequired;
  private final String httpAuthUsername;
  private final String httpAuthPassword;

  @Autowired
  public HttpAuthManager(@Value("${countercheck.http-auth.required}") boolean httpAuthRequired,
                         @Value("${countercheck.http-auth.username}") String httpAuthUsername,
                         @Value("${countercheck.http-auth.password}") String httpAuthPassword) {
    this.httpAuthRequired = httpAuthRequired;
    this.httpAuthUsername = httpAuthUsername;
    this.httpAuthPassword = httpAuthPassword;
  }

  public boolean isHttpAuthRequired() {
    return this.httpAuthRequired;
  }

  public String getHttpAuthUsername() {
    return this.httpAuthUsername;
  }

  public String getHttpAuthPassword() {
    return this.httpAuthPassword;
  }

  public String toString() {
    return this.httpAuthRequired ? "HTTP Auth being used" : "HTTP Auth not being used";
  }
}
