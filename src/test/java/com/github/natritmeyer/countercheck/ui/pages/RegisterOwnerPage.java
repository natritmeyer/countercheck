package com.github.natritmeyer.countercheck.ui.pages;

import com.microsoft.playwright.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegisterOwnerPage {
  private final Page page;

  @Autowired
  public RegisterOwnerPage(Page page) {
    this.page = page;
  }

  public void registerWithDetails(String firstName, String lastName, String address, String city, String telephoneNumber) {
    this.page.locator("input[name='firstName']").fill(firstName);
    this.page.locator("input[name='lastName']").fill(lastName);
    this.page.locator("input[name='address']").fill(address);
    this.page.locator("input[name='city']").fill(city);
    this.page.locator("input[name='telephone']").fill(telephoneNumber);
    submitDetails();
  }

  private void submitDetails() {
    this.page.locator("button[type=submit]").click();
  }
}
