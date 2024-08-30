## Coding Task - CRUD API

1. Prepare application providing CRUD REST API for Asset Management System 
2. You can imagine it will be used to build frontend that users of the Asset
   Management System will interact with 
3. The application must expose REST API and be able to save assets to some
   persistent storage

4. Requirements
   1. Asset attributes
      1. Name 
      2. Type 
      3. Description
   2. Asset operations:
      1. Retrieving list of assets 
      2. Retrieving individual assets 
      3. Adding new assets 
      4. Deleting assets 
      5. Updating assets
   3. Assets can be added to groups
   4. Assets can be in multiple groups
   5. Group attributes
      1. Name 
      2. Description 
   6. Group operations 
      1. Retrieving list of groups 
      2. Adding new groups 
      3. Adding assets to assetGroup 
      4. Removing assets from assetGroup 
      5. Retrieving assetGroup’s assets 
   7. Tech stack:
      1. Java 17/Kotlin 
      2. Spring Boot 
      3. Hibernate 
      4. Maven or Gradle 
   8. Tests - executed in the build process 
   9. Docker Compose file to run full application along with dependencies like database

5. Please deliver it as a GitHub project. We would prefer to see some commit
   history.

   
## Setup

Run database at the start:

`docker-compose up -d postgres`

To build app run:

`./gradlew clean build`

To run application in locally:

`./gradlew bootRun`

To build application docker image run:

`docker build -t asset_management_service_image .`

To database with application in docker:

`docker-compose up`

Path to API documentation:

`http://localhost:8080/swagger-ui/index.html`