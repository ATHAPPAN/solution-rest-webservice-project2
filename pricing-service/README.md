# Pricing Service

The Pricing Service is a REST WebService that simulates a backend that
would store and retrieve the price of a vehicle given a vehicle id as
input. 


## Features

- REST WebService integrated with Spring Boot

## Instructions

#### TODOs => Completed

- Modify the pom.xml added all dependencies
- Pricing Service has been converted into microservices by Creating  project with the Eureka server at the port 8761 by configuring appropriate application.properties and POM.xml
- Added an additional test to check whether the application appropriately generates a price for a given vehicle ID

#### Run the code

To run this service you execute:

```
$ mvn clean package
```

```
$ java -jar target/pricing-service-0.0.1-SNAPSHOT.jar
```

It can also be imported in your IDE as a Maven project.
