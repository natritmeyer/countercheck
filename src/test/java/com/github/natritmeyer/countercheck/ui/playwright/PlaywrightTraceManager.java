package com.github.natritmeyer.countercheck.ui.playwright;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Tracing;
import java.nio.file.Path;
import org.junit.jupiter.api.MediaType;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestReporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PlaywrightTraceManager {
  private static final String ZIP = "application/zip";

  private final Logger log = LoggerFactory.getLogger(this.getClass());

  private final BrowserContext browserContext;
  private final boolean tracingEnabled;
  private final String tracePath;

  @Autowired
  public PlaywrightTraceManager(BrowserContext browserContext,
                                @Value("${countercheck.playwright.tracing.enabled}") boolean tracingEnabled,
                                @Value("${countercheck.playwright.tracing.output-path}") String tracePath) {
    this.browserContext = browserContext;
    this.tracingEnabled = tracingEnabled;
    this.tracePath = tracePath;

    log.debug("Tracing enabled: {}", tracingEnabled);
  }

  public void startTrace() {
    if (tracingEnabled) {
      log.debug("Starting playwright trace");
      this.browserContext
          .tracing()
          .start(new Tracing.StartOptions()
              .setScreenshots(true)
              .setSnapshots(true));
    }
  }

  public void stopTrace(TestInfo testInfo, TestReporter testReporter) {
    if (tracingEnabled) {
      log.debug("Stopping playwright trace");
      Path fullTracePath = Path.of(
          String.format(
              this.tracePath + "/%s_%s.zip",
              testInfo.getTestClass().get().getSimpleName(),
              testInfo.getTestMethod().get().getName()));
      this.browserContext
          .tracing()
          .stop(new Tracing.StopOptions()
              .setPath(fullTracePath));
      testReporter.publishFile(fullTracePath, MediaType.parse(ZIP));
    }
  }
}
