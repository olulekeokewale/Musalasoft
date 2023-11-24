# Drones
Musala Drone Project

## Introduction
There is a major new technology that is destined to be a disruptive force in the field of transportation: **the drone**. Just as the mobile phone allowed developing countries to leapfrog older technologies for personal communication, the drone has the potential to leapfrog traditional transportation infrastructure.

This project provides  a REST API service that allows users to communicate with the drones.

> Specific communication with the drone is outside the scope of this project.

## Technologies
* [Spring Framework](https://spring.io/)
* [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
* [H2 Database](http://h2database.com/html/main.html)

## Dependencies
* [Java](https://www.java.com/en/)
* [Maven](https://maven.apache.org/) 

## Compilation
To build the project, open a terminal at the root of the project and run the following command:
```
mvn clean install
```

## Execution
Open a terminal at the root of the project and run the following command:
```
java -jar target/drones-0.0.1-SNAPSHOT.jar
```
The application will start and be available at `http://localhost:8080`.
 
## Features
### Dispatch - Drone and Medication Management
####  [POST Method]  Register a new Drone- http://localhost:8080//api//management//drones//register-drone
* Sample Request Json 
```JSON
{
    "serialNumber":"0001-001",
    "weightLimit":500,
    "batteryCapacity":100,
    "droneModel":"LIGHTWEIGHT",
    "droneState":"IDLE"
}
```
* Sample Response Json 
```JSON
{
    "message": "Drone Addedd to the queue",
    "status": "Success"
}
```

#### [GET Method] Get Drones Avalailable for Loading - http://localhost:8080//api//management//drones//get-available-drones
* Sample Response Json 
```JSON
[
    {
        "drone": {
            "id": 1,
            "serialNumber": "0001-001",
            "weightLimit": 500.0,
            "batteryCapacity": 100,
            "droneModel": "LIGHTWEIGHT",
            "droneState": "IDLE",
            "medications": []
        },
        "capacityAvailable": 500.0
    },
    {
        "drone": {
            "id": 2,
            "serialNumber": "0001-002",
            "weightLimit": 500.0,
            "batteryCapacity": 100,
            "droneModel": "LIGHTWEIGHT",
            "droneState": "IDLE",
            "medications": []
        },
        "capacityAvailable": 500.0
    }
]
```
#### [POST Method] Load a Drone with Medications - http://localhost:8080//api//management//drones//load-drone
#### [GET Method] Get List of Medications in a Drone - http://localhost:8080//api//management//drones//get-drone-medications
#### [GET Method] Get battery level of a drone - http://localhost:8080//api//management//drones//get-drone-batterylevel 


### Medications
#### [GET Method]  All Medications - http://localhost:8080//api//management//medications//getall-medications
#### [POST Method] Save a Medication - http://localhost:8080//api//management//medications//create-medication
#### [POST Method] Find a Medication by id - http://localhost:8080//api//management//medications//id//{id}
#### [POST Method] Find a Medication by name - http://localhost:8080//api//management//medications//findbyname/{name}
#### [DELETE Method] Delete a Medication by id - http://localhost:8080//api//management//medications//delete/{id}

### Drones
#### [GET Method]  All Drones - http://localhost:8080//api//management//drones
#### [POST Method] Save a Drone - http://localhost:8080//api/management/drones//saveDrone
#### [POST Method] Find a Drone by id - http://localhost:8080//api/management/drones//findDrone/{id}
#### [DELETE Method] Delete a Drone by id - http://localhost:8080//api//management//drones//deleteDrone//{id}

