# cybercube-be-task-2023

Let’s create a multi-module application that manages profiles and their analyses. All the communication is meant to be via REST.

Every profile contains the following information:


```
{ 
  "name": "Alice", 
  "surname": "Willson", 
  "budget": "21505.78"
} 
```
The system operates with two types of analyses: FIRST and SECOND. 

Every analysis type has a price, for example, 1500.15 and 2180.40, respectively (must be provided via config file). 

Every analysis contains the following information: 



```
{ 
  "type": "SECOND", 
  "owner": "<owner's identifier>",
  "viewers": ["<viewer1's identifier>", "<viewer2's identifier>"],
  "hiddenInfo": "a comment from the owner"
}
```

Every analysis has one owner’s profile and 0 or more view profiles. 
Every time a profile runs a new analysis, the profile’s budget gets adjusted. When the budget is low, and no analysis is affordable, the system rejects triggering a new analysis. 

### Scenarios: 
1.  Profile can be created, updated, and fetched; 
2. Profile can create an analysis; 
3. Profile can fetch the analysis with the hiddenInfo if he is the owner;
4. Profile can fetch the analysis without the hiddenInfo if he is the viewer;
5. Profile can’t fetch an analysis if he is not an owner or viewer; 
6. Profile can list all the analyses where he is an owner or viewer from NOW to PAST order;
7. Profile can’t trigger a new analysis if his budget is below the price; 
8. Analysis can’t contain links on non-registered profiles. 

 
### Comments and requirements:

1. No securitization is required within the technical task. 
2. We do not expect to use Spring Security in the task.  For simplicity, profileId must be passed in the header of the REST calls to differentiate profiles.
3. API versioning, logging, tests, and following the code organization principles must be considered in the result implementation. 
4. Code and infrastructure have to be dockerized. 
5. End-to-end tests must be placed in another module in the repo and run in a separate container as a part of the docker-compose run. 
Thus, containers must be launched in the following order: DB → Application → E2e.
6. The analyses' prices must be specified in the application.yml file. 
7. The project has to be accompanied by a README.md file. 
8. Every entity in the project can include more fields than specified in JSON examples - these are carrying the fields only required for explaining the task. 

### Technologies and tools: 
Spring Boot, PostgreSQL/MariaDB (based on your preference), Docker.


# How to run project locally

You will need Docker installed on your machine, then from the terminal type:
```
docker-compose up
```

Profile budget is configurable in application.yml:
```
analysis:
  price:
    first: 1500.15
    second: 2180.40
```

Call following endpoints based on your needs:
```
###
POST http://localhost:8081/analysis-app/v1/profile/create-profile
Content-Type: application/json

{ 
  "name": "Alice", 
  "surname": "Willson", 
  "budget": "21505.78"
} 

###
PUT http://localhost:8081/analysis-app/v1/profile/update-profile
Content-Type: application/json
profile-Id: id

{ 
  "name": "Alice", 
  "surname": "Willson", 
  "budget": "21505.78"
} 

###
GET http://localhost:8081/analysis-app/v1/profile/find-profile-by-id
profile-Id: <profile's identifier>

###
POST http://localhost:8081/analysis-app/v1/analysis/create-analysis
Content-Type: application/json

{ 
  "type": "SECOND", 
  "owner": "<owner's identifier>",
  "viewers": ["<viewer1's identifier>", "<viewer2's identifier>"],
  "hiddenInfo": "a comment from the owner"
}

###
GET http://localhost:8081/analysis-app/v1/analysis/find-analysis-by-owner-id
profile-Id: <owner's identifier>


###
GET http://localhost:8081/analysis-app/v1/analysis/find-analysis-by-viewer-id
profile-Id: <viewer2's identifier>

###
GET http://localhost:8081/analysis-app/v1/analysis/find-analysis-by-owner-or-viewer-id
profile-Id: <owner's identifier> or <viewer2's identifier>



```

 
