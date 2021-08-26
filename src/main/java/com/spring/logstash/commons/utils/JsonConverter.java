package com.spring.logstash.commons.utils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.spring.logstash.exception.JsonException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;
import org.springframework.stereotype.Component;

@Component
public class JsonConverter {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
  private static final ObjectMapper OBJECT_MAPPER_WRAP_ROOT_VALUE = new ObjectMapper();
  private static final String TIME_ZONE_AMERICA_SAO_PAULO = "America/Sao_Paulo";

  static {
    OBJECT_MAPPER.setSerializationInclusion(Include.NON_NULL);
    OBJECT_MAPPER.setSerializationInclusion(Include.NON_EMPTY);
    OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    OBJECT_MAPPER.setTimeZone(TimeZone.getTimeZone(TIME_ZONE_AMERICA_SAO_PAULO));
    OBJECT_MAPPER.registerModule(new JavaTimeModule());

    OBJECT_MAPPER_WRAP_ROOT_VALUE.configure(SerializationFeature.WRAP_ROOT_VALUE, true);
    OBJECT_MAPPER_WRAP_ROOT_VALUE.setSerializationInclusion(Include.NON_NULL);
    OBJECT_MAPPER_WRAP_ROOT_VALUE.setSerializationInclusion(Include.NON_EMPTY);
    OBJECT_MAPPER_WRAP_ROOT_VALUE.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
    OBJECT_MAPPER_WRAP_ROOT_VALUE.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
        false);
    OBJECT_MAPPER_WRAP_ROOT_VALUE.setTimeZone(TimeZone.getTimeZone(TIME_ZONE_AMERICA_SAO_PAULO));
  }

  public void registerModule(SimpleModule module) {
    OBJECT_MAPPER.registerModule(module);
  }

  public <T> T readValue(String content, Class<T> valueType) {
    if (content == null) {
      return null;
    }

    try {
      return OBJECT_MAPPER.readValue(content, valueType);
    } catch (Exception e) {
      throw new JsonException(e);
    }
  }

  public <T> List<T> readList(String content, Class<T> type) {
    try {
      return OBJECT_MAPPER.readValue(content,
          TypeFactory.defaultInstance().constructCollectionType(List.class, type));
    } catch (Exception e) {
      throw new JsonException(e);
    }
  }

  public String writeValueAsString(Object value) {
    try {
      return OBJECT_MAPPER.writeValueAsString(value);
    } catch (Exception e) {
      throw new JsonException(e);
    }
  }

  public String writeValueAsStringWrapped(Object value) {
    try {
      return OBJECT_MAPPER_WRAP_ROOT_VALUE.writeValueAsString(value);
    } catch (Exception e) {
      throw new JsonException(e);
    }
  }

  public <T> T convertObject(Object obj, Class<T> type) {
    return readValue(writeValueAsString(obj), type);
  }

  public <T> T convertObjectIgnoreJsonProperty(Object obj, Class<T> type) {
    ObjectMapper OM = new ObjectMapper().configure(MapperFeature.USE_ANNOTATIONS, false);
    try {
      return OM.readValue(OM.writeValueAsString(obj), type);
    } catch (IOException e) {
      throw new JsonException(e);
    }
  }

  public <T, E> List<T> convertList(List<E> fromList, Class<T> toType) {
    List<T> toList = new ArrayList<>();
    fromList.forEach(element -> {
      toList.add(convertObject(element, toType));
    });
    return toList;
  }
}
