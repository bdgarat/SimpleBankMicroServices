# Simple Bank Micro Services

- ¿Qué es? 
Es un proyecto que encaré a modo de práctica de construcción de microservicios conectados entre sí a través de llamadas sincronicas (Feign) y asincronicas (RabbitMQ) y desplegados en Docker (y proximamente kubernetes).

- ¿Que tecnologías usa (al momento)?
1. Java 25
2. Spring boot
3. Spring Data JPA (+ PostgreSQL)
4. RabbitMQ
5. Docker Compose

- ¿Que recursos utilizaste para armarlo?
Me basé principalmente en un tutorial de Stive Tech que encontré y me pareció super valioso, ya que explica a detenimiento los conceptos de microservicios que estaba buscando. El link a la playlist es el siguiente: [Playlist Stive Tech](https://www.youtube.com/playlist?list=PLkiIEFVLQKqjy2aIGfHlNAe51TC1wlIYt)
Algunos apartados fueron modificados de la guía, por ejemplo el servicio utilizado para colas de mensajería, donde en el tutorial se sugería Azure Service Bus y se cambió a RabbitMQ.

- Documentacion adicional
1. Coleccion de postman (WIP)
2. Diagrama en draw.io del estado actual (WIP)

