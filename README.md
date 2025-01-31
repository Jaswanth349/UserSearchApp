# UserSearchApp
This is a Java full-stack project.
-------------------------
Backend: Spring Boot

Frontend: Angular

Data Communication: Kafka

Searching: Hibernate Search

Database: H2 (in-memory)

Container Orchestration: Docker

----------------------
How to Run:
----------
1. Download the project zip file.
2. Backend: Place it at C:\PROJECT\BACKEND\user-search-api
3. Frontend: Place it at C:\PROJECT\FRONTEND\ANGULAR\user-search-app
4. Docker Compose file: Place it at C:\PROJECT
5. Go to the directory where you saved the docker-compose.yml file.
6. Run the command: docker-compose up --build. This will build the Docker images.
        Note: Make sure you have Docker installed and running on your local machine before you run this command.
7. After the build is complete, check your Docker application and click "Run" to start the containers.
8. Check in browser http://localhost:4200/
----------------------------------
If you want to run project in LOCAL:
----------------------------------
1. Download the project zip file.
2. Backend: Place it at C:\PROJECT\BACKEND\user-search-api
3. Go here C:\PROJECT\BACKEND\user-search-api and mvn clean, mvn install and start application
4. Frontend: Place it at C:\PROJECT\FRONTEND\ANGULAR\user-search-app
5. Go here C:\PROJECT\FRONTEND\ANGULAR\user-search-app and npm install and start ng serve.
6. Check in browser http://localhost:4200/
-----
Note: Before starting backend do below kafka settings.
-----
Kafka Settings:
---------------
1. Just download kafka from here(https://dlcdn.apache.org/kafka/3.9.0/kafka_2.13-3.9.0.tgz)
2. Extract kafka to C:\kafka
3. Start Zookeeper and Kafka
	.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties

	.\bin\windows\kafka-server-start.bat .\config\server.properties
--------------------------------------------
For more details about the project, refer to the attached documents
