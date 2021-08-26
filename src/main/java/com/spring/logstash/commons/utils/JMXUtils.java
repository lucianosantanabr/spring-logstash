package com.spring.logstash.commons.utils;

import java.lang.management.ManagementFactory;

public class JMXUtils {

  public static String getRuntimeMXBean() {
    return ManagementFactory.getRuntimeMXBean().getName();
  }

}
