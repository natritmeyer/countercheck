package com.github.natritmeyer.countercheck.support.ui.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OwnerListPage {
  private static final String MOST_RECENT_OWNER_COLUMN_SELECTOR_FORMATTER = "owner-list > table > tbody > tr:last-child > td:nth-child(%d)";

  private final Page page;

  @Autowired
  public OwnerListPage(Page page) {
    this.page = page;
  }

  public Locator getMostRecentOwnersNameCell() {
    return getMostRecentOwnersCellByColumnNo(1);
  }

  public Locator getMostRecentOwnersAddressCell() {
    return getMostRecentOwnersCellByColumnNo(2);
  }

  public Locator getMostRecentOwnersCityCell() {
    return getMostRecentOwnersCellByColumnNo(3);
  }

  public Locator getMostRecentOwnersTelephoneCell() {
    return getMostRecentOwnersCellByColumnNo(4);
  }

  public void showMostRecentOwnerDetails() {
    getMostRecentOwnersNameCell().click();
  }

  private Locator getMostRecentOwnersCellByColumnNo(int columnNo) {
    String cellSelector = String.format(MOST_RECENT_OWNER_COLUMN_SELECTOR_FORMATTER, columnNo);
    return this.page.locator(cellSelector);
  }
}
