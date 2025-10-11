package com.github.natritmeyer.countercheck.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
    "com.github.natritmeyer.countercheck.config.features",
    "com.github.natritmeyer.countercheck.support.testdata",
    "com.github.natritmeyer.countercheck.model.domain",
    "com.github.natritmeyer.countercheck.model.requestbodies"
})
public class CountercheckConfig {
}
