package com.microservices.apigateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {

  @Bean
  public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
    return builder
      .routes()
      .route(p ->
        p
          .path("/get")
          .filters(f ->
            f
              .addRequestHeader("MyHeader", "MyURI")
              .addRequestParameter("Param", "MyValue")
          )
          .uri("http://httpbin.org:80")
      )
      .route(p -> p.path("/currrency-exchange/**")
        .uri("lb://currency-exchange"))
      .build();
  }
}
