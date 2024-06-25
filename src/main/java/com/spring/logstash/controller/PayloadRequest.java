package com.spring.logstash.controller;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PayloadRequest implements Serializable {

  private static final long serialVersionUID = 4877748064632930654L;

  private String id;
  private String message;

}
