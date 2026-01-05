# prueba-sofka

Para dar solución a la prueba fullstack 
Construida con Angular en el frontend y Spring Boot WebFlux en el backend, utilizando Server-Sent Events (SSE) para comunicación reactiva.

## Backend -- Java
Lo realice usando 
 IDE Intellij 2025.2 Community Edition. 
 Java 21. 
 Spring WebFlux,
 Arquitectura Hexagonal (Ports & Adapters)
 Project Reactor
 SSE (TEXT_EVENT_STREAM)
 Gradle
 JUnit 5 / Mockito / AssertJ


## Frontend -- Angular
Lo realice usando 
 Angular Signals 
 Angular Material
 RxJS
 Server-Sent Events (EventSource)
 
Las version del proyecto angular es la siguientes
Angular CLI: 20.3.13
Node: 22.21.1
Package Manager: npm 10.9.4
OS: win32 x64

Angular: 20.3.15
... common, compiler, compiler-cli, core, forms
... platform-browser, router

Package                      Version
------------------------------------
@angular-devkit/architect    0.2003.13

@angular-devkit/core         20.3.13

@angular-devkit/schematics   20.3.13

@angular/build               20.3.13

@angular/cdk                 20.2.14

@angular/cli                 20.3.13

@angular/material            20.2.14

@schematics/angular          20.3.13

rxjs                         7.8.2

typescript                   5.9.3


## Deployment 
Para poner a correr Angular, 

cd prueba

npm install

ng serve


Y queda disponible en http://localhost:4200

Para poner a correr las pruebas unitarias java

cd transacciones

./gradlew test

Para correr el proyecto Java

cd transacciones

./gradlew bootRun

Queda corriendo en http://localhost:9090

## Comentarios
En la raiz del proyecto se encuentra un video de la aplicacion funcionando y el archivo de postman para probar los endpoint.
| Método | Endpoint             | Descripción           |
| ------ | -------------------- | --------------------- |
| POST   | /transactions        | Crear transacción     |
| GET    | /transactions        | Obtener transacciones |
| GET    | /transactions/stream | Stream SSE            |



👤 Autor
Vanessa Luna
Prueba técnica – Desarrollo Fullstack
