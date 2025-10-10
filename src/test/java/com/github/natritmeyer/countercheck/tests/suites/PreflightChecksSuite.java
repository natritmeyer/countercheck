package com.github.natritmeyer.countercheck.tests.suites;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("Countercheck Preflight Checks")
@SelectPackages({"com.github.natritmeyer.countercheck.tests.preflightchecks"})
public class PreflightChecksSuite {
}
