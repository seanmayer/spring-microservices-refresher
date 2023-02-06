package com.microservices.currencyconversionservice;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyConversion {

  private Long id;
  private String from,to;
  private BigDecimal quantity, conversionMultiple, totalCalculatedAmount;
  private String environment;
}
