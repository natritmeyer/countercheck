package com.natritmeyer.countercheck.tests.suites;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("Countercheck Smoke Tests")
@SelectPackages({"com.natritmeyer.countercheck.tests.smoketests"})
public class SmokeTestSuite {
}
