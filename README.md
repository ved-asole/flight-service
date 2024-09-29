# CrimsonSky - Flight Service Microservice

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
