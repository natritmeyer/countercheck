package com.natritmeyer.countercheck.config.common;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
    "com.natritmeyer.countercheck.config.common",
    "com.natritmeyer.countercheck.support.common",

})
public class CountercheckCommonConfig {
}
