package com.github.natritmeyer.countercheck.ui.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OwnerDetailsPage {
  private static final String OWNER_DETAILS_ROW_SELECTOR_FORMATTER = "owner-details > table > tbody > tr:nth-child(%d) > td";

  private final Page page;

  @Autowired
  public OwnerDetailsPage(Page page) {
    this.page = page;
  }

  public Locator getOwnerNameCell() {
    return getOwnerDetailsCellByColumnNo(1);
  }

  public Locator getOwnerAddressCell() {
    return getOwnerDetailsCellByColumnNo(2);
  }

  public Locator getOwnerCityCell() {
    return getOwnerDetailsCellByColumnNo(3);
  }

  public Locator getOwnerTelephoneCell() {
    return getOwnerDetailsCellByColumnNo(4);
  }

  private Locator getOwnerDetailsCellByColumnNo(int rowNo) {
    String cellSelector = String.format(OWNER_DETAILS_ROW_SELECTOR_FORMATTER, rowNo);
    return this.page.locator(cellSelector);
  }
}
