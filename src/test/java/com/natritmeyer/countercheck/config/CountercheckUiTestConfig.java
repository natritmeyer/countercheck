package com.natritmeyer.countercheck.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
    "com.natritmeyer.countercheck.config.common",
    "com.natritmeyer.countercheck.config.ui",
    "com.natritmeyer.countercheck.support.ui",
})
public class CountercheckUiTestConfig {
}
