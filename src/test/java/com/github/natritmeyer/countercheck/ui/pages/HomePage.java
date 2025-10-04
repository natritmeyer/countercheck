package com.github.natritmeyer.countercheck.ui.pages;

import com.microsoft.playwright.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HomePage {
  private final Page page;

  @Autowired
  public HomePage(Page page) {
    this.page = page;
  }

  public void load() {
    this.page.navigate("http://localhost:8080");
  }

  public void navigateToRegisterNewOwnerPage() {
    this.page.locator("a[ui-sref='ownerNew']").click();
  }

  public Page getPage() {
    return page;
  }
}
