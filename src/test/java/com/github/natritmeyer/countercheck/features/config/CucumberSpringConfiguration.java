package com.github.natritmeyer.countercheck.features.config;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.test.context.ContextConfiguration;

@CucumberContextConfiguration
@ContextConfiguration(classes = {CountercheckCucumberConfig.class})
public class CucumberSpringConfiguration {
}
