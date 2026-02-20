# Simple Bank Micro Services

## ¿De que ese trata el proyecto? 
Es una implementación simulada de microservicios que podríamos encontrar en un banco u otra entidad financiera. Sobre la misma existe un servicio encargado de registrar e informar los clientes (sbms-customer-service), quienes pueden abrir cuentas a su nombre y llevar un registro de su balance (sbms-account-service) y realizar desembolsos de credito, los cuales modifican tales montos (sbms-credit-disbursement-service). Adicionalmente a tal servicios core del ejercicio propuesto, se encuentran el servicio de notificacion (sbms-notification-service) que se encarga de recibir de forma asincronica el medido de enviar un alerta a un email informado. Adicionalmente, se encuentra la gestión y acceso de tales servicios a través de (sbms-gateway-service) y la seguridad provista por (sbms-security-service).

![Diagrama arquitectura sbms](diagrama_arq_sbms.jpg)

## ¿Que se buscó durante la implementación?
El objetivo fue aplicar en un entorno simulado pero respetando la realidad de diferentes microservicios, sus interacciones y herramientas adicionales que son utilizadas para llevar a cabo funciones criticas, donde el uptime, alta cohesión y bajo acoplamiento son necesidades a la hora de realizar la especificación, diseño e implementación de soluciones.

## ¿Que tecnologías usa (al momento)?
1. Java 21
2. Spring boot 2025.1.0
3. Spring Data JPA 2025.1.0 (+ PostgreSQL)
4. Spring Security 2025.1.0 + OAuth2
5. Spring Cloud 2025.1.0 + Resilience4j (Circuit Breaker)
6. RabbitMQ + AMPQ
7. Database caching using Redis
8. Docker Compose
9. Mailtrap.io (Plataforma de envio de mails SMTP)

## ¿Que recursos/documentación fueron utilizados para llevar a cabo el proyecto?
Me basé principalmente en un tutorial de Stive Tech que encontré y me pareció super valioso, ya que explica a detenimiento los conceptos de microservicios que estaba buscando. El link a la playlist es el siguiente: [Playlist Stive Tech](https://www.youtube.com/playlist?list=PLkiIEFVLQKqjy2aIGfHlNAe51TC1wlIYt).
Algunos apartados fueron modificados de la guía, por ejemplo el servicio utilizado para colas de mensajería, donde en el tutorial se sugería Azure Service Bus y se cambió a RabbitMQ, utilizando la siguiente guía: [Quick setup & implementation of RabbitMQ using Spring Boot 3.0 & Java 17](https://medium.com/@pkumarsaha71/quick-setup-implementation-of-rabbitmq-using-spring-boot-3-0-java-17-3b637c8adece). y la inclusión de una caché de la base de datos Postgres utilizando Redis, para mejorar la velocidad de acceso a datos frecuentemente utilizados.

## ¿Como se levanta todo esto?
1. Primero, tendrás que levantar los container de PostgreSQL y RabbitMQ utilizando el siguiente comando en una consola de windows*:
`docker compose up -d`
2. Será necesario levantar cada servicio, utilizando sobre cada uno el siguiente comando:
`mvn spring-boot:run`

* Para macOS/Linux será necesario adaptar los comandos aquí descritos

## Endpoints disponibles
### sbms-customer-service: {localhost}:8083
- (GET) /customers/cu/{cu} -> Obtener un customer por Cu
- (GET) /customers/id/{id} -> Obtener un customer por id
- (GET) /customers -> Obtener todos los customers
- (POST) /customers -> Agregar un customer
- (PUT) /customers -> Actualizar un customer
- (DELETE) /customers -> Eliminar un customer

### sbms-account-service: {localhost}:8081
- (GET) /accounts/id/{id} -> Obtener un account por id
- (GET) /accounts -> Obtener todas las accounts
- (POST) /accounts -> Agregar un account
- (PUT) /accounts -> Actualizar un account
- (DELETE) /accounts -> Eliminar un account
- (PUT) /accounts/deposit -> Agregar un deposit en un account 
- (GET) /actuator/circuitbreakers -> Obtener los circuit breakers

### sbms-credit-disbursement-service: {localhost}:8082
- (GET) /credit-disbursements/id/{id} -> Obtener un credit disbursement por id
- (GET) /credit-disbursements -> Obtener todos los credit disbursements
- (POST) /credit-disbursements -> Agregar un credit disbursement

### sbms-security-service: {localhost}:9000
- (POST) /oauth2/token -> Obtener un token

### sbms-gateway-service: {localhost}:8080
 - (GET) /swagger-ui/index.html -> Swagger de servicios mediante gateway
 - (GET) /v3/api-docs -> OpenAPI definition JSON

### sbms-notification-service: {localhost}:8084

## Esquemas de DTOs
- CustomerDTO (String id, String cu, String name, String email, String mobile, String address)
- AccountDTO (String id, String accountNumber, String accountName, BigDecimal accountBalance, CustomerDTO customer)
- DepositDTO (String accountNumber, BigDecimal amount, String customerCu)
- CreditDisbursementDTO (String id, BigDecimal amount,Integer termMonths, BigDecimal interestRate, String accountNumber, String customerCu)

## Documentacion adicional
1. Coleccion de postman: ./SimpleBankMicroServices WIP.postman_collection.json (WIP)
2. Diagrama en draw.io del estado actual: ./diagrama_arq_sbms.drawio (WIP)

