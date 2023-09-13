# Phorest Service

## Technologies
* Maven 3+
* Java 17
* Spring Boot 2.7.16
* JPA with H2 database
* Testing - Junit, Mockito, MockMvc, Integration Test using H2 database

## Build and Run

### How to build:
`./mvnw clean install`

### How to run tests:
`./mvnw clean tests`


### How to run
* Run as a Spring Boot local application
* `./mvnw clean install spring-boot:run -Dspring.profiles.active=local`

## Overview

### Summary
| Endpoint               | method | Status                                              | Notes                                 |
|:-----------------------|:-------|:----------------------------------------------------|:--------------------------------------|
| `/api/v1/upload`       | POST   | Response code 201 and Location header, 400, 404 500 | Need to use import-type in the header |
| `/api/v1/clients/{id}` | GET    | Response code 200, 404, 500                         |                                       |
| `/api/v1/clients/{id}` | DELETE | Response code 200, 500                              |                                       |
| `/api/v1/top-clients`  | GET    | Response code 200, 500                              |                                       | 

### Assumptions
* Used Spring Boot 2 as this was the version is used two years ago.
* Spent a lot of time understanding opencsv and "uploading" a multipart form data. I have not parsed a file in a long time.
* There is one endpoint to upload the csv files (appointments, clients, services, purchases).
* The code base is set up as components e.g. Upload, Appointment, Client, Product (Service & Purchase, Data)
* I have tried to add unit tests and service tests where possible. I have focused more on the Integration tests using H2 database with the uploaded csv files.
* Used H2 Database as it is easier for integration testing. Using MySQL would be added more overhead with a docker compose file to spin up a MySQL instance.

### Further Improvements
* Implement Swaggerhub auto generated documentation.
* Implement Validation bean for the CSV file, header and rows.
* Improve the upload endpoint to upload multiple csv files (appointments, clients, services, purchases) at the same time. Simplify the service.
* Implement the update endpoint.


## Problem description

**Comb as You Are** have decided to ditch their old salon software provider

and upgrade to *Phorest* to avail of its best in class client retention tools.

They're excited to finally offer their clients the opportunity to book online.

They've exported their clients appointment data from their old provider and

would like to email their top 50 most loyal clients of the past year with the news

that they can now book their next appointment online.

****Problem Spec****

The exported data is split across 4 files.

- **clients.csv**
- **appointments.csv**
- **services.csv**
- **purchases.csv**

Each client has many appointments and are related through a `client_id` property on the appointment

Each appointment has many services and are related through an `appointment_id` property on the service

Each appointments has 0 or many purchases and are related through an `appointment_id` property on the purchase

Services and purchases have an associated number of loyalty points defined as a property

Clients have a boolean banned property defined on the client.