package com.github.natritmeyer.countercheck.testdata;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.charset.Charset;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class TestDataRetriever {
  public <T> T fromJsonFile(Resource resource, TypeReference<T> typeReference) {
    try {
      String resourceContentAsString = resource.getContentAsString(Charset.defaultCharset());

      ObjectMapper objectMapper = new ObjectMapper();
      return objectMapper.readValue(resourceContentAsString, typeReference);
    } catch (IOException ioException) {
      throw new RuntimeException(ioException);
    }
  }
}
