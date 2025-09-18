package com.github.natritmeyer.countercheck.suites;

import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@IncludeEngines("cucumber")
@SelectPackages({
    "features",
    "com.github.natritmeyer.countercheck.features.config",
    "com.github.natritmeyer.countercheck.features.steps"
})
public class FeaturesSuite {
}
