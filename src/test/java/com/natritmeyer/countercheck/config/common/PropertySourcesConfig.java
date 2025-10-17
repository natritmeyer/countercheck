package com.natritmeyer.countercheck.config.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
    @PropertySource("classpath:/countercheck.properties"),
    @PropertySource("classpath:/environments/${countercheck.environment:local}.properties")
})
public class PropertySourcesConfig {
}
