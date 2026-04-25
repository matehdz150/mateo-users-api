# Users API

REST API for user management and authentication built with Spring Boot.  
Includes CRUD operations, login, validations, encryption, Swagger documentation, unit tests, and Docker support.

---

## Tech Stack

- Java 17
- Spring Boot
- Maven
- JUnit / Mockito
- Swagger / OpenAPI
- Docker

---

## Run Locally
./mvnw spring-boot:run

## Run with docker
docker compose up --build

## Base URL
http://localhost:8080

## Swagger Documentation
http://localhost:8080/swagger-ui/index.html

##Endpoints
* GET /users
* POST /users
* PATCH /users/{id}
* DELETE /users/{id}
* POST /login

## Query Examples
GET /users?sortedBy=name
GET /users?filter=name+co+Ana
GET /users?filter=email+ew+mail.com
GET /users?filter=phone+sw+555
GET /users?filter=tax_id+eq+AARR990101XXX

## Run Tests
./mvnw test
