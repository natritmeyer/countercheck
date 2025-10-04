package com.github.natritmeyer.countercheck.ui.config;

import com.github.natritmeyer.countercheck.ui.playwright.CountercheckPlaywrightBrowserType;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import java.nio.file.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = {"classpath:/playwright.properties"})
@ComponentScan(basePackages = {"com.github.natritmeyer.countercheck.ui.pages"})
public class CountercheckPlaywrightConfig {
  private final Playwright playwright;
  private final Browser browser;
  private final BrowserContext browserContext;
  private final Page page;

  @Autowired
  public CountercheckPlaywrightConfig(@Value("${countercheck.playwright-browser-type}") String playwrightBrowserTypeName) {
    Playwright.CreateOptions createOptions = new Playwright.CreateOptions();
    this.playwright = Playwright.create(createOptions);

    BrowserType browserType = CountercheckPlaywrightBrowserType
        .parse(playwrightBrowserTypeName)
        .getBrowserType(playwright);
    BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions();
    this.browser = browserType.launch(launchOptions);

    Browser.NewContextOptions newContextOptions = new Browser.NewContextOptions();
    newContextOptions.setRecordVideoDir(Path.of("target/video"));
    this.browserContext = browser.newContext(newContextOptions);

    this.page = browserContext.newPage();
  }

  @Bean
  public Playwright getPlaywright() {
    return this.playwright;
  }

  @Bean
  public Browser getBrowser() {
    return this.browser;
  }

  @Bean
  public BrowserContext getBrowserContext() {
    return this.browserContext;
  }

  @Bean
  public Page getPage() {
    return this.page;
  }
}
