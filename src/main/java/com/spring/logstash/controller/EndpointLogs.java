package com.spring.logstash.controller;

import com.spring.logstash.commons.utils.ResponseUtility;
import com.spring.logstash.commons.utils.StringUtility;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/logs", produces = MediaType.APPLICATION_JSON_VALUE)
public class EndpointLogs implements StringUtility, ResponseUtility {


  @ApiOperation(value = "Get status from an Logs", tags = "Logs")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Logs status returned", response = String.class),
      @ApiResponse(code = 204, message = "Logs not found"),
      @ApiResponse(code = 401, message = "Not Authorized. Authentication required")
  })
  //@formatter:on
  @GetMapping(path = "/status")
  public ResponseEntity<String> getStatus(@RequestParam String message) {

    log.debug("Mensagem={} ", message);
    return ok("Status logs API");
  }

  @ApiOperation(value = "Post Payload from an Logs", tags = "Logs")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Logs status returned", response = String.class),
      @ApiResponse(code = 204, message = "Logs not found"),
      @ApiResponse(code = 401, message = "Not Authorized. Authentication required")
  })
  @PostMapping
  public ResponseEntity<String> create(@RequestBody PayloadRequest request) {

    log.debug("id={} Mensagem={} ", request.getId(), request.getMessage());
    return ok("Post logs API");
  }

}
