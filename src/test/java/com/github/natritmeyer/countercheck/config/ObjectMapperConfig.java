package com.github.natritmeyer.countercheck.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfig {
  private final ObjectMapper objectMapper;

  @Autowired
  public ObjectMapperConfig() {
    this.objectMapper = new ObjectMapper();
    this.objectMapper.findAndRegisterModules();
  }

  @Bean
  public ObjectMapper getObjectMapper() {
    return objectMapper;
  }
}
