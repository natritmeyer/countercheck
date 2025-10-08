package com.github.natritmeyer.countercheck.acceptancetests;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.github.natritmeyer.countercheck.ui.config.CountercheckPlaywrightConfig;
import com.github.natritmeyer.countercheck.ui.pages.HomePage;
import com.github.natritmeyer.countercheck.ui.pages.OwnerDetailsPage;
import com.github.natritmeyer.countercheck.ui.pages.OwnerListPage;
import com.github.natritmeyer.countercheck.ui.pages.RegisterOwnerPage;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Tracing;
import java.nio.file.Paths;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(CountercheckPlaywrightConfig.class)
public class OrderManagementTest {
  private static final String FIRST_NAME = "Legolas";
  private static final String LAST_NAME = "Greenleaf";
  private static final String ADDRESS = "Elven King's Halls";
  private static final String CITY = "Mirkwood";
  private static final String TELEPHONE_NUMBER = "987654321098";

  private final Logger log = LoggerFactory.getLogger(this.getClass());

  private final HomePage homePage;
  private final RegisterOwnerPage registerOwnerPage;
  private final OwnerListPage ownerListPage;
  private final OwnerDetailsPage ownerDetailsPage;
  private final BrowserContext browserContext;

  @Autowired
  public OrderManagementTest(HomePage homePage,
                             RegisterOwnerPage registerOwnerPage,
                             OwnerListPage ownerListPage,
                             OwnerDetailsPage ownerDetailsPage,
                             BrowserContext browserContext) {
    this.homePage = homePage;
    this.registerOwnerPage = registerOwnerPage;
    this.ownerListPage = ownerListPage;
    this.ownerDetailsPage = ownerDetailsPage;
    this.browserContext = browserContext;
  }

  @BeforeEach
  public void setup() {
    this.browserContext
        .tracing()
        .start(new Tracing.StartOptions()
        .setScreenshots(true)
        .setSnapshots(true));

    this.homePage.load();
  }

  @AfterEach
  public void teardown() {
    this.browserContext
        .tracing()
        .stop(new Tracing.StopOptions()
        .setPath(Paths.get("target/trace.zip")));
  }

  @Test
  public void addNewOwner() {
    this.homePage.navigateToRegisterNewOwnerPage();
    this.registerOwnerPage.registerWithDetails(FIRST_NAME, LAST_NAME, ADDRESS, CITY, TELEPHONE_NUMBER);

    assertThat(this.ownerListPage.getMostRecentOwnersNameCell()).hasText(String.format("%s %s", FIRST_NAME, LAST_NAME));
    assertThat(this.ownerListPage.getMostRecentOwnersAddressCell()).hasText(ADDRESS);
    assertThat(this.ownerListPage.getMostRecentOwnersCityCell()).hasText(CITY);
    assertThat(this.ownerListPage.getMostRecentOwnersTelephoneCell()).hasText(TELEPHONE_NUMBER);

    this.ownerListPage.showMostRecentOwnerDetails();
    assertThat(this.ownerDetailsPage.getOwnerNameCell()).hasText(String.format("%s %s", FIRST_NAME, LAST_NAME));
    assertThat(this.ownerDetailsPage.getOwnerAddressCell()).hasText(ADDRESS);
    assertThat(this.ownerDetailsPage.getOwnerCityCell()).hasText(CITY);
    assertThat(this.ownerDetailsPage.getOwnerTelephoneCell()).hasText(TELEPHONE_NUMBER);
  }

  @Test
  public void addAnotherOwner() {
    this.homePage.navigateToRegisterNewOwnerPage();
    this.registerOwnerPage.registerWithDetails(FIRST_NAME, LAST_NAME, ADDRESS, CITY, TELEPHONE_NUMBER);

    assertThat(this.ownerListPage.getMostRecentOwnersNameCell()).hasText(String.format("%s %s", FIRST_NAME, LAST_NAME));
    assertThat(this.ownerListPage.getMostRecentOwnersAddressCell()).hasText(ADDRESS);
    assertThat(this.ownerListPage.getMostRecentOwnersCityCell()).hasText(CITY);
    assertThat(this.ownerListPage.getMostRecentOwnersTelephoneCell()).hasText(TELEPHONE_NUMBER);

    this.ownerListPage.showMostRecentOwnerDetails();
    assertThat(this.ownerDetailsPage.getOwnerNameCell()).hasText(String.format("%s %s", FIRST_NAME, LAST_NAME));
    assertThat(this.ownerDetailsPage.getOwnerAddressCell()).hasText(ADDRESS);
    assertThat(this.ownerDetailsPage.getOwnerCityCell()).hasText(CITY);
    assertThat(this.ownerDetailsPage.getOwnerTelephoneCell()).hasText(TELEPHONE_NUMBER);
  }
}
