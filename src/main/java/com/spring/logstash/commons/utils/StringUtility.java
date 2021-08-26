package com.spring.logstash.commons.utils;

import java.util.Optional;
import org.springframework.util.StringUtils;

public interface StringUtility {

  default Optional<String> findFirst(String... strings) {
    for (String str : strings) {
      if (StringUtils.hasLength(str)) {
        return Optional.of(str);
      }
    }
    return Optional.empty();
  }

}
