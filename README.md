# Spring Microservices Refresher

- Naming server - http://localhost:8761/
- Microservice 1 - http://localhost:8000/
- Microservice 2 - http://localhost:8001/
- H2 Database - http://localhost:8080/h2-console
- Gateway - http://localhost:8765
  - http://localhost:8765/currency-exchange/from/USD/to/INR
  - http://localhost:8765/currency-conversion/from/USD/to/INR/quantity/10
  - http://localhost:8765/currency-conversion-feign/from/USD/to/INR/quantity/10
  - http://localhost:8765/currency-conversion-new/from/USD/to/INR/quantity/10


## Introduction
- Spring boot
- Spring Cloud
- Spring Cloud Gateway
- Resilience4j
- Docker
- Kubernetes

## Microservices Overview

![Screenshot](readme/images/microservices-overview-1.png)

## Naming Server

In a microservice architecture, a naming server serves as a registry for service discovery. Each microservice in the system has a unique name and runs on its own address, and the naming server maintains a mapping between the service names and their addresses. When a client wants to access a certain microservice, it can query the naming server to find the address of the service it needs. This eliminates the need for hard-coded addresses and allows for dynamic reconfiguration of the system as services come and go. The naming server plays a crucial role in the seamless functioning of a microservice-based system.

![Screenshot](readme/images/naming-server.png)

In this example we use Eureka naming server.

![Screenshot](readme/images/naming-server-eureka.png)

## Load Balancing

In a microservice architecture, a load balancer is an important component that sits between the client and the microservices. Its primary function is to distribute incoming requests to multiple instances of a microservice, improving system reliability and scalability.

When a client makes a request, the load balancer routes it to one of the available instances of the target microservice. This helps to evenly distribute the incoming request load, avoid overloading any single instance, and prevent a single point of failure. In case an instance of the microservice fails, the load balancer can detect this and redirect requests to other healthy instances.

By using a load balancer, microservice-based systems can dynamically scale up or down the number of instances of a service based on the incoming request volume, ensuring that the system remains responsive and resilient even under high load.

![Screenshot](readme/images/load-balancer-and-naming-server.png)

## API Gateway (Spring Cloud Gateway)

An API gateway is a single entry point for all requests from clients to the backend services in a microservice architecture. It sits between the client and the microservices and routes requests to the appropriate microservice. It also provides a single place to apply common cross-cutting concerns such as security, monitoring, and resiliency.

## Docker

- Docker is a tool designed to make it easier to create, deploy, and run applications by using containers.

- A container is a standard unit of software that packages up code and all its dependencies so the application runs quickly and reliably from one computing environment to another.

- A Docker container image is a lightweight, standalone, executable package of software that includes everything needed to run an application: code, runtime, system tools, system libraries and settings.

![Screenshot](readme/images/docker.png)

## Docker Architecture

![Screenshot](readme/images/docker-architecture.png)

### Docker example commands

- `docker events` - show events
- `docker info` - show info
- `docker stats` - show stats metrics
- `docker system df` - show disk usage of docker
- `docker inspect <container id>` - show details of container
- `docker top <container id>` - show processes running inside container
- `docker ps` - list running containers
- `docker ps -a` - list all containers
- `docker images` - list images
- `docker run <image-name>` - run image
- `docker run <image-name> -m 512m --cpu-quota 50000` - run image with memory and cpu limits (512mb and 50% of cpu)
- `docker run -p 8080:8080 -d <image-name>` - detach flag (run container in background - no terminal)
- `docker run -p 8080:8080 --restart=always <image-name>` - docker container will auto run on docker engine start
- `docker run -p 8080:8080 <image-name>` - run image and map port 8080 to 8080
- `docker logs <container id>` - show logs
- `docker logs <container id> -f` - show logs and follows
- `docker image history <image-name>` - show history of image
- `docker image inspect <image-name>` - show details of image
- `docker run -p 8080:8080 -e "SPRING_PROFILES_ACTIVE=dev" <image-name>` - run image and map port 8080 to 8080 and set environment variable
- `docker run -p 8080:8080 -e "SPRING_PROFILES_ACTIVE=dev" -e "SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/currency_exchange" <image-name>` - run image and map port 8080 to 8080 and set environment variable
- `docker run -p 8080:8080 -e "SPRING_PROFILES_ACTIVE=dev" -e "SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/currency_exchange" -e "SPRING_DATASOURCE_USERNAME=root" -e "SPRING_DATASOURCE_PASSWORD=root" <image-name>` - run image and map port 8080 to 8080 and set environment variable
- `docker run -p 8080:8080 -e "SPRING_PROFILES_ACTIVE=dev" -e "SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/currency_exchange" -e "SPRING_DATASOURCE_USERNAME=root" -e "SPRING_DATASOURCE_PASSWORD=root" -e "SPRING_DATASOURCE_DRIVER-CLASS-NAME=com.mysql.cj.jdbc.Driver" <image-name>` - run image and map port 8080 to 8080 and set environment variable
- `docker run -p 8080:8080 -e "SPRING_PROFILES_ACTIVE=dev" -e "SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/currency_exchange" -e "SPRING_DATASOURCE_USERNAME=root" -e "SPRING_DATASOURCE_PASSWORD=root" -e "SPRING_DATASOURCE_DRIVER-CLASS-NAME=com.mysql.cj.jdbc.Driver" -e "SPRING_JPA_HIBERNATE_DDL-AUTO=update" <image-name>` - run image and map port 8080 to 8080 and set environment variable
- `docker run -p 8080:8080 -e "SPRING_PROFILES_ACTIVE=dev" -e "SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/currency_exchange" -e "SPRING_DATASOURCE_USERNAME=root" -e "SPRING_DATASOURCE_PASSWORD=root" -e "SPRING_DATASOURCE_DRIVER-CLASS-NAME=com.mysql.cj.jdbc.Driver" -e "SPRING_JPA_HIBERNATE_DDL-AUTO=update" -e "SPRING_JPA_SHOW-SQL=true" <image-name>` - run image and map port 8080 to 8080 and set environment variable


### Spring Cloud Gateway

![Screenshot](readme/images/spring-cloud-gateway.png)

- Simple effective way to route APIs
- Provide cross-cutting concerns like:
  - security
  - monitoring/metrics
  - resiliency
- Built on top of Spring WebFlux framework(non-blocking, reactive)
- Features:
  - Matching routes on any request attribute
  - Dynamic routing
  - Predicates
  - Filters
  - Integrates with Spring Cloud DiscoveryClient (Load Balancing - Eureka, Consul, Zookeeper, etc)
  - Path Rewriting

## Circuit Breaker (Resilience4j)

- The library is inspired by Hystrix 
- but offers a much more convenient API and a number of other features:
    - Rate Limiter (block too frequent requests)
    - Bulkhead (avoid too many concurrent requests)
    - Retry (automatically retry failed requests)
    - Cache (avoid duplicate requests)
    - Circuit Breaker (avoid cascading failures)
    - Time Limiter (avoid too long running requests)
    - Event Listeners (monitoring)

![Screenshot](readme/images/resilience4j.png)

### Testing endpoint with multiple requests using Apache Bench

- Open terminal
- Run `ab -n 1000 -c 100 http://localhost:8000/sample-api`

## Issues
### Running multiple springboot microservice projects in VSCode
- This is a known issue https://github.com/microsoft/vscode-java-debug/issues/606, https://github.com/eclipse/eclipse.jdt.ls/issues/1137, which caused the new package not recognized during building workspace. It's expected to be fixed in vscode-java Middle October release.

Current workaround is reload VS Code window, or F1 ->Clean the java language server workspace.

Reference: https://stackoverflow.com/questions/57857855/could-not-find-or-load-main-class-vs-code