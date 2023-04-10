package com.microservices.currencyexchangeservice;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CircuitBreakerController {

 private Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);

  @GetMapping("/sample-api")
  @Bulkhead(name = "default")
  public String sampleApi() {
    logger.info("Sample API call received");
    return "sample-api";
  }

  public String hardcodedResponse(Exception ex) {
    return "fallback-response";
  }
}
