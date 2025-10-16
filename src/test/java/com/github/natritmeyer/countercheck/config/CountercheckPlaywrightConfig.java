package com.github.natritmeyer.countercheck.config;

import com.github.natritmeyer.countercheck.support.network.HttpAuthManager;
import com.github.natritmeyer.countercheck.support.network.ProxyManager;
import com.github.natritmeyer.countercheck.support.playwright.CountercheckPlaywrightBrowserType;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.HttpCredentials;
import com.microsoft.playwright.options.Proxy;
import java.nio.file.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = {
    "com.github.natritmeyer.countercheck.config",
    "com.github.natritmeyer.countercheck.support",
    "com.github.natritmeyer.countercheck.model.pages"
})
@PropertySource(value = {"classpath:/playwright.properties"})
public class CountercheckPlaywrightConfig {
  private final Logger log = LoggerFactory.getLogger(this.getClass());

  private final Playwright playwright;
  private final Browser browser;
  private final BrowserContext browserContext;
  private final Page page;
  private final ProxyManager proxyManager;
  private final HttpAuthManager httpAuthManager;

  @Autowired
  public CountercheckPlaywrightConfig(@Value("${countercheck.playwright.browser-type}") String playwrightBrowserTypeName,
                                      ProxyManager proxyManager,
                                      HttpAuthManager httpAuthManager) {
    this.proxyManager = proxyManager;
    this.httpAuthManager = httpAuthManager;

    this.playwright = Playwright.create();

    BrowserType browserType = CountercheckPlaywrightBrowserType
        .parse(playwrightBrowserTypeName)
        .getBrowserType(playwright);

    BrowserType.LaunchOptions launchOptions = createLaunchOptions();
    this.browser = browserType.launch(launchOptions);

    Browser.NewContextOptions newContextOptions = createNewContextOptions();
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

  private BrowserType.LaunchOptions createLaunchOptions() {
    BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions();

    log.debug("Proxy config: {}", this.proxyManager.toString());
    if (this.proxyManager.isProxyRequired()) {
      String proxy = String.format("http://%s:%d",
          this.proxyManager.getProxyHost(),
          this.proxyManager.getProxyPort());

      launchOptions.setProxy(
          new Proxy(proxy)
              .setUsername(this.proxyManager.getProxyUsername())
              .setPassword(this.proxyManager.getProxyPassword()));
    }

    return launchOptions;
  }

  private Browser.NewContextOptions createNewContextOptions() {
    Browser.NewContextOptions newContextOptions = new Browser.NewContextOptions();

    log.debug("HTTP Auth config: {}", this.httpAuthManager.toString());
    newContextOptions.setRecordVideoDir(Path.of("target/video"));

    if (this.httpAuthManager.isHttpAuthRequired()) {
      newContextOptions.setHttpCredentials(
          new HttpCredentials(
              this.httpAuthManager.getHttpAuthUsername(),
              this.httpAuthManager.getHttpAuthPassword()));
    }

    return newContextOptions;
  }
}
