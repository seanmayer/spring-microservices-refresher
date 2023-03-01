package com.microservices.currencyconversionservice;
import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CurrencyConversionController {

  @Autowired
  private CurrencyExchangeProxy proxy;

  @Autowired
  private RestTemplate restTemplate;

  @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
  public CurrencyConversion calculateCurrencyConversion(
    @PathVariable String from,
    @PathVariable String to,
    @PathVariable BigDecimal quantity
  ) {

    HashMap<String,String> uriVariables = new HashMap<>();
    uriVariables.put("from",from);
    uriVariables.put("to",to);

    ResponseEntity<CurrencyConversion> responseEntity = restTemplate.getForEntity(
      "http://localhost:8000/currency-exchange/from/{from}/to/{to}",
      CurrencyConversion.class,uriVariables
    );

    CurrencyConversion response = responseEntity.getBody();

    return new CurrencyConversion(
      response.getId(),
      from,
      to,
      quantity,
      response.getConversionMultiple(),
      quantity.multiply(response.getConversionMultiple()),
      response.getEnvironment() + " rest template"
    );
  }

  @GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
  public CurrencyConversion calculateCurrencyConversionFeign(
    @PathVariable String from,
    @PathVariable String to,
    @PathVariable BigDecimal quantity
  ) {

    CurrencyConversion response = proxy.retrieveExchangeValue(from, to);

    return new CurrencyConversion(
      response.getId(),
      from,
      to,
      quantity,
      response.getConversionMultiple(),
      quantity.multiply(response.getConversionMultiple()),
      response.getEnvironment() + " feign"
    );
  }
}
