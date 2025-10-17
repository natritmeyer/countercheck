package com.github.natritmeyer.countercheck.support.ui;

import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum CountercheckPlaywrightBrowserType {
  chromium {
    @Override
    public BrowserType getBrowserType(Playwright playwright) {
      return playwright.chromium();
    }
  },
  firefox {
    @Override
    public BrowserType getBrowserType(Playwright playwright) {
      return playwright.firefox();
    }
  },
  webkit {
    @Override
    public BrowserType getBrowserType(Playwright playwright) {
      return playwright.webkit();
    }
  };

  public static CountercheckPlaywrightBrowserType parse(String playwrightBrowserName) {
    return Stream.of(values())
        .filter(knownCountercheckPlaywrightBrowserTypeType -> knownCountercheckPlaywrightBrowserTypeType.name().equals(playwrightBrowserName))
        .findFirst()
        .orElseThrow(() -> {
          String message = String.format("Unknown browser type '%s'. Valid options: %s",
              playwrightBrowserName,
              Stream.of(values())
                  .map(CountercheckPlaywrightBrowserType::name)
                  .collect(Collectors.joining(", ")));
          return new RuntimeException(message);
        });
  }

  public abstract BrowserType getBrowserType(Playwright playwright);
}
