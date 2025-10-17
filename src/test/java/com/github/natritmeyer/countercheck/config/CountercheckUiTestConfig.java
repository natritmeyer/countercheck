package com.github.natritmeyer.countercheck.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
    "com.github.natritmeyer.countercheck.config.common",
    "com.github.natritmeyer.countercheck.config.ui",
    "com.github.natritmeyer.countercheck.support.ui",
})
public class CountercheckUiTestConfig {
}
