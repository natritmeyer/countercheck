package com.github.natritmeyer.countercheck.testdata;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.charset.Charset;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class TestDataRetriever {
  private final ObjectMapper objectMapper;

  public TestDataRetriever() {
    this.objectMapper = new ObjectMapper();
    this.objectMapper.findAndRegisterModules();
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
