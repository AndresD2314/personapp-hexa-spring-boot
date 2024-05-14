# PersonApp-Hexa-Spring-Boot

Este repositorio contiene la implementación de una aplicación que sigue los principios de arquitectura limpia, diseñada para gestionar información relacionada con personas, incluyendo sus profesiones, estudios y números de teléfono asociados.

La aplicación ofrece dos formas de interacción: a través de una API REST documentada con Swagger y mediante una interfaz de línea de comandos (CLI).

## Requisitos

Antes de ejecutar este proyecto, asegúrate de tener Maven instalado en tu máquina. Además, se recomienda habilitar las extensiones o plugins de Lombok y Spring Boot en tu entorno de desarrollo. Si prefieres ejecutar el proyecto de forma manual, las bases de datos se configuran en los archivos `application.properties` con los siguientes puertos:

- MariaDB: `localhost:3306`
- MongoDB: `localhost:27017`

### Ejecución con Docker

**Instrucciones de Ejecución**

**Paso 1:** Construir los contenedores Docker

Ejecuta el siguiente comando para construir los contenedores Docker necesarios:

```bash
docker-compose build

**Paso 2:** Levantar los servicios
Levanta todos los servicios definidos en el archivo docker-compose.yml con el siguiente comando: `docker-compose up`

## Ejecutar la aplicacion CLI

Para probar la aplicación CLI, ejecuta el siguiente comando: `docker-compose run -it personapp-cli`

## Detener personapp-rest para ejecutar la aplicación CLI:

Si deseas detener personapp-rest y ejecutar la aplicación CLI, usa el siguiente comando:`docker stop personapp-rest`

### Estado Inicial de las Bases de Datos

##MariaDB

La base de datos de MariaDB está inicialmente poblada con los datos de la entidad persona.

##MongoDB

La base de datos de MongoDB tiene creado el documento persona.


## Instrucciones de Ejecución

- Para utilizar el API REST, inicia el proyecto relacionado con `rest-input-adapter`. Luego, accede a la aplicación en ejecución en tu navegador favorito mediante el enlace `http://localhost:3000`.
- Para utilizar la interfaz CLI, inicia el proyecto relacionado con `cli-input-adapter`. Esto te permitirá acceder a todas las funcionalidades de la aplicación desde la terminal de comandos.

## Licencia

Este proyecto está bajo la Licencia Apache.

## Colaboradores

- Juan Manuel Aguiar
- Andres Felipe Duarte
- Humberto Rueda Cataño
