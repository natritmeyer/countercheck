package com.github.natritmeyer.countercheck.support.common;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.charset.Charset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class TestDataRetriever {
  private final ObjectMapper objectMapper;

  @Autowired
  public TestDataRetriever(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  public <T> T fromJsonFile(Resource resource, TypeReference<T> typeReference) {
    try {
      String resourceContentAsString = resource.getContentAsString(Charset.defaultCharset());
      return objectMapper.readValue(resourceContentAsString, typeReference);
    } catch (IOException ioException) {
      throw new RuntimeException(ioException);
    }
  }
}
