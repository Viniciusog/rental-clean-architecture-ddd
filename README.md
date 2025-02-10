## Spring Rental Application - Domain Drive Design (DDD)

Esse projeto é uma API Java Spring feita utilizando Clean Architecture juntamente com Domain Drive Design, 
seguindo os princípios do SOLID para melhor estruturação do código.
<br> Contém **mais de 260 testes**, incluindo testes unitários, testes de integração e end-to-end com JUnit e Mockito.

### Tecnologias usadas 
Java 21. <br>
Spring 3. <br>
Apache Maven 3.9.1. <br>
Liquibase. <br>
JUnit. <br>
Mockito. <br>

## Rotas

### Car
* Create Car <br>
POST /car
* Delete car by id <br>
DELETE /car/{id} 
* Get All Cars <br>
GET /car 
* Get Car By Id <br>
GET /car/{id} 
* Update car <br>
PUT /car/{id}
* Get car availability <br>
GET /car/availability/{id}

### Customer
* Create customer <br>
POST /customer
* Activate customer <br>
PATCH /customer/{id}/activate 
* Deactivate customer <br>
PATCH /customer/{id}/deactivate
* Get customer by id <br>
GET /customer/{id}
* Get customers by filter: <br>
GET /customer?nameStarting={Name}&nameContaining={Containing}
* Delete customer <br>
DELETE /customer/{id}
* Update customer <br>
UPDATE /customer/{id}

### Rental
* Create Rental controller <br>
POST /rental
* Get all rentals <br>
GET /rental
* Get rental by id <br>
GET /rental/{id}
* Get rentals by card id and time range <br>
GET /rental/search?cardId={}&startTime={}&endTime={}
* Get rentals by customer id <br>
GET /rental/customer/{id}
* Update rental <br>
UPDATE /rental/{id}
* Delete rental by id
DELETE /rental/{id}