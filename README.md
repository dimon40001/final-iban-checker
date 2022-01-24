# Luminor Home Assignment (Java test)

https://luminor.ee/

## Description

Create two REST endpoints for IBAN Number checking ( https://en.wikipedia.org/wiki/International_Bank_Account_Number )
as backend and create front end that will incorporate these REST Endpoints.

Requirements:

1. Mandatory

- Validate IBAN – Returns whether the IBAN is valid and writes result to DB (HashMap)
- Can validate 1 IBAN
- Can validate a list of IBANs

2. Optional

- Get history of IBANs from DB (HashMap) with filtering "VALID", "INVALID", "ALL"

It should contain:

- algorithm itself (better, if you do not use library from internet 😉)
- unit or integration tests
- documentation
- installation procedure

Mandatory technologies:

- Java 11 or newer (must be built on open JDK)
- Git
- SpringBoot
- Build tool (Maven/Gradle)
- React (Angular, Vue)

Optional:

- Swagger
- H2 or PostgreSQL SQL Database
- Docker

P.S Some form (nothing complex) of a front-end is mandatory

The deadline is in a week.

## How to build and use

### Quick start

Requirements:

- the recommended execution environment is Linux, running under Windows hasn't been tested
- ensure that Java 11 is installed using `java -version`
- ensure Docker and docker-compose tools are installed

Run `demo.sh` for Linux (set necessary execution permissions with `chmod +x demo.sh`) or run `demo.bat` for Windows
platform (not recommended).

In the web-browser navigate to:

- Application frontend: http://localhost:1080
- Backend API: http://localhost:1080/ibans
- Swagger UI: http://localhost:1080/swagger-ui.html

### Build details

1. Navigate to the root directory of the cloned project.
2. Run `mvn clean spring-boot:build-image` to compile the app and build the docker image with the application. Docker
   image is build using `spring-boot-maven-plugin`
3. Run `cd devops && docker-compose up -d` to start the environment with the application and the PostgreSQL database
4. To stop the application run `docker-compose down`
