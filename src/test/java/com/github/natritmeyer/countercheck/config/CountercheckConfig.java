package com.github.natritmeyer.countercheck.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
    @PropertySource("classpath:/countercheck.properties"),
    @PropertySource("classpath:/environments/${countercheck.environment:local}.properties")
})
@ComponentScan(basePackages = {
    "com.github.natritmeyer.countercheck.config",
    "com.github.natritmeyer.countercheck.testdata"
})
public class CountercheckConfig {
}
