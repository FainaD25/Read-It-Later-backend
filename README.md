# Read It Later

## Overview

This is a backend application built with Java and Gradle. This README will guide you through the steps to build, run, and Dockerize the application. Authors: Karoliina Kannik, Aet-Kadi Kald, Faina Dõmša

## Project introduction

ReadItLater is a platform that allows users to make a personal wishlist of books they want to read in the future. Users can add books to the website for everyone to see and they can also add them to their wishlist. There’s an option to search for books by genre—by selecting a genre, users can view books belonging to it. Books can also be searched by author or title. Clicking on a book allows users to see available reviews for that book. Users can set their favorite genres, which are used to provide personalized book recommendations. Each recommendation also includes the option to view reviews related to the book.

## Used technologies

* Postgresql and Liquibase - database
* Vue.js - frontend
* Spring Boot - backend
* Gradle - build tool
* Lombok
* Mapstruct
* JWT

## Prerequisites

Before you begin, ensure you have met the following requirements:

- Java JDK 21 installed
- Gradle installed (optional, as it's included in the project)
- Docker installed

## How to Build and Run the Application

1. Clone the repository:
2. Run the application using Gradle:
```
./gradlew clean build
java -jar .\build\libs\iti0302-2024-backend-0.0.1-SNAPSHOT.jar
```
3. Access the application at http://localhost:8080

## Creating and Running a Docker Container

1. In the docker-compose.yml file in the root of this project change the following refrences:

* Replace the web-project and fronted services image names with your Docker Hub username and the desired image name and tag.

* Add your database user and password after POSTGRES_USER and POSTGRES_PASSWORD.

2. Run the application using Docker Compose:
```
docker-compose up -d
```

3. Stop the application using Docker Compose:
```
docker-compose down
```


