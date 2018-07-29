It is the backend of the Walmart Task Replenisher Application which can be deployed on any cloud platform and function as an independent api.

Currently, the backend itself is deployed at url : http://18.218.226.1:8080/

Below are the specifications which needs to be taken care of before the api functionalities are utilized:

1. Assuming that MySql server is already installed in the running system, start mysql service:
	mysql -u root -p password

2. Either change the sql creadentials to:
	username - root
	password - password
		
		OR

  Make changes in application.properties file inside path 'task-replenisher-api/src/main/resources' and update the mysql user credentials

3. Run following command on mysql prompt to create a database schema:
	mysql>CREATE DATABASE task_replenisher

4. Inside file application.properties(from step 2), change
	spring.jpa.hibernate.ddl-auto=update
		TO
	spring.jpa.hibernate.ddl-auto=create

5. Build the project by running 'mvn clean install' at the root folder and run the jar inside the target using:
	java -jar task-replenisher-api-0.0.1-SNAPSHOT.jar

6. This will create the tables in the database.

7. Run below command on sql prompt to load initial role data:
	insert into role values('ADMIN'),('BUSINESS'),('INDIVIDUAL');

8. Again, inside file application.properties, revert
	spring.jpa.hibernate.ddl-auto=create
		TO
	spring.jpa.hibernate.ddl-auto=update

9. Build and run again using step 5.

Once the jar has been executed, the service is up and running at http://localhost:8080/ and can be consumed using any REST client.

Below are the possible requests which can be made :









