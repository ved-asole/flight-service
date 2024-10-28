# CrimsonSky - Flight Service Microservice

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=ved-asole_flight-service&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=ved-asole_flight-service)
[![Maven Checks](https://github.com/ved-asole/flight-service/actions/workflows/maven-checks.yml/badge.svg)](https://github.com/ved-asole/flight-service/actions/workflows/maven-checks.yml)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=ved-asole_flight-service&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=ved-asole_flight-service)

[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=ved-asole_flight-service&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=ved-asole_flight-service)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=ved-asole_flight-service&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=ved-asole_flight-service)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=ved-asole_flight-service&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=ved-asole_flight-service)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=ved-asole_flight-service&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=ved-asole_flight-service)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=ved-asole_flight-service&metric=bugs)](https://sonarcloud.io/summary/new_code?id=ved-asole_flight-service)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=ved-asole_flight-service&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=ved-asole_flight-service)
## Live Link / Demo Link: 🔗
Access the flight service at **[localhost:8080](http://localhost:8080)**

## Table of Contents: 📑

- [About The Service](#about-the-service-)
- [Technologies](#technologies-%EF%B8%8F)
- [Setup](#setup-)
- [Approach](#approach-)
- [Status](#status-)
- [License](#license-%EF%B8%8F)

## About the Service: 📚
The **Flight Service** is a core part of the CrimsonSky flight booking system. It manages real-time flight data, schedules, and availability. The service is built with **Spring Boot** and uses **MongoDB** for flight data and **Redis** for caching, ensuring efficient data retrieval.

## Technologies: ☕️

- Java
- Spring Boot
- Spring Web with HATEOAS
- MongoDB (NoSQL)
- Redis
- Swagger OpenAPI

## Setup: 💻

- **Install Java 21 :**
    - Download and install **[Java 21](https://www.oracle.com/in/java/technologies/downloads/#java21)**
    - Set up the **JAVA_HOME** environment variable.
  

- **Install Maven:**
    - Download and install **[Maven](https://maven.apache.org/download.cgi)**
    - Add the **bin** directory to the **PATH** environment variable.

**Available Scripts:**

In the project directory, you can run:

- #### `mvn install`
    Installs all necessary dependencies.

- #### `mvn spring-boot:run`
    Runs the flight service.\
    Open [http://localhost:8080](http://localhost:8080) to access it.

# Approach: 🚶
This service is part of the **CrimsonSky** microservices ecosystem, focusing on real-time flight search and caching with **Redis** for optimal performance.

# Status: 📶
Service under development 🛠️

# License: ©️
MIT License (**[Check Here](LICENSE)**)