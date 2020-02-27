# CRUD operations with Spring Boot and React
 
The application contains API and CRUD for Products and Categories. UI is populated with a React app.

**Prerequisites:** Java 8, Node.js 8+ and MySQL.

## Getting Started

To install the application, run the following commands:

```bash
git clone https://github.com/golosax/crud.git crud
cd crud
```

This will get a copy of the project installed locally. To install all of its dependencies and start each app, follow the instructions below.

First of all setup MySQL database using `configure-mysql.sql` file and check datasource configuration in `application.yml`

To run the server, execute:
 
```bash
./mvnw spring-boot:run
```
or run `Application.java`

To run the client, go into the `app` folder and execute:
 
```bash
npm install
```

then run:
```bash
npm start
```

## Swagger Documentation

Swagger-generated documentation is available by path `{YOUR_HOST}/swagger-ui.html`

## Currency Exchange service

For currency rates http://fixer.io service with "Free Plan" is integrated.  
