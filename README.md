# User Management System

## Technologies

Project is created with: 

* Springframework.boot
* Mysql
* Lombok
* Geoip2
* JUnit


## Setup

To run this project: 
```
  cd ../spring
  docker pull mysql
  docker pull openjdk:18-oracle
  mvn package
  docker build -t user-management-system-backend .
  docker-compose up
```

