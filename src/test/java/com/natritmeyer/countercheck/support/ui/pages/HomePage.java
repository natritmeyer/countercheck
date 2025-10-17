package com.natritmeyer.countercheck.support.ui.pages;

import com.microsoft.playwright.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HomePage {
  private final Page page;
  private final String url;

  @Autowired
  public HomePage(Page page,
                  @Value("${countercheck.aut.ui-service.scheme}") String scheme,
                  @Value("${countercheck.aut.ui-service.host}") String host,
                  @Value("${countercheck.aut.ui-service.port}") int port) {
    this.page = page;
    this.url = String.format("%s://%s:%d", scheme, host, port);
  }

  public void load() {
    this.page.navigate(url);
  }

  public void navigateToRegisterNewOwnerPage() {
    this.page.locator("a[ui-sref='ownerNew']").click();
  }
}
