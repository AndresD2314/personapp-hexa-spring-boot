# PersonApp-Hexa-Spring-Boot

Este repositorio contiene la implementación de una aplicación que sigue los principios de arquitectura limpia, diseñada para gestionar información relacionada con personas, incluyendo sus profesiones, estudios y números de teléfono asociados.

La aplicación ofrece dos formas de interacción: a través de una API REST documentada con Swagger y mediante una interfaz de línea de comandos (CLI).

## Requisitos

Antes de ejecutar este proyecto, asegúrate de tener Maven instalado en tu máquina. Además, se recomienda habilitar las extensiones o plugins de Lombok y Spring Boot en tu entorno de desarrollo. Si prefieres ejecutar el proyecto de forma manual, las bases de datos se configuran en los archivos `application.properties` con los siguientes puertos:

- MariaDB: localhost:3306
- MongoDB: localhost:27017

## Instrucciones de Ejecución

- Para utilizar el API REST, inicia el proyecto relacionado con `rest-input-adapter`. Luego, accede a la aplicación en ejecución en tu navegador favorito mediante el enlace `http://localhost:3000`.
- Para utilizar la interfaz CLI, inicia el proyecto relacionado con `cli-input-adapter`. Esto te permitirá acceder a todas las funcionalidades de la aplicación desde la terminal de comandos.

## Licencia

Este proyecto está bajo la Licencia Apache.

## Colaboradores

- Juan Manuel Aguiar
- Andres Felipe Duarte
- Humberto Rueda Cataño
